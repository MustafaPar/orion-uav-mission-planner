package com.uavfleet.service;

import com.uavfleet.dto.AssignmentResultDto;
import com.uavfleet.entity.*;
import com.uavfleet.exception.NoEligibleUavException;
import com.uavfleet.exception.ResourceNotFoundException;
import com.uavfleet.repository.AssignmentRepository;
import com.uavfleet.repository.MissionLogRepository;
import com.uavfleet.repository.MissionRepository;
import com.uavfleet.repository.UavRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Core mission-assignment algorithm.
 *
 * For every AVAILABLE UAV that is physically capable of completing the
 * mission (battery + range constraints), a composite score is computed.
 * The UAV with the LOWEST score wins the assignment ("cheapest" candidate).
 *
 * score = distanceScore + batteryScore + availabilityScore + priorityScore
 *
 *  - distanceScore     -> straight-line distance to the mission (km). Closer is better.
 *  - batteryScore      -> 100 - batteryLevel. Fuller batteries score lower (better).
 *  - availabilityScore -> 0 for AVAILABLE UAVs, a large penalty otherwise (effectively disqualifies them).
 *  - priorityScore      -> negative offset that rewards assigning capable UAVs to higher
 *                          priority missions faster (keeps best-equipped UAVs in the pool
 *                          for the missions that matter most).
 *
 * Hard eligibility filters (applied before scoring):
 *  - UAV must be AVAILABLE.
 *  - UAV's remaining range (battery-adjusted) must reach the mission location and back.
 *  - UAV's max range must be >= the mission's required range.
 */
@Service
public class AssignmentService {

    private static final double UNAVAILABLE_PENALTY = 100_000.0;
    private static final double MIN_BATTERY_RESERVE_PERCENT = 15.0; // safety margin kept after the round trip

    private final UavRepository uavRepository;
    private final MissionRepository missionRepository;
    private final AssignmentRepository assignmentRepository;
    private final MissionLogRepository missionLogRepository;

    public AssignmentService(UavRepository uavRepository,
                              MissionRepository missionRepository,
                              AssignmentRepository assignmentRepository,
                              MissionLogRepository missionLogRepository) {
        this.uavRepository = uavRepository;
        this.missionRepository = missionRepository;
        this.assignmentRepository = assignmentRepository;
        this.missionLogRepository = missionLogRepository;
    }

    @Transactional
    public AssignmentResultDto assignBestUav(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found: " + missionId));

        List<Uav> candidates = uavRepository.findByStatus(UavStatus.AVAILABLE);

        Optional<ScoredUav> best = candidates.stream()
                .map(uav -> scoreUav(uav, mission))
                .filter(scored -> scored.eligible)
                .min(Comparator.comparingDouble(s -> s.score));

        if (best.isEmpty()) {
            throw new NoEligibleUavException(
                    "No eligible UAV found for mission '" + mission.getTitle() +
                            "' (battery/range constraints not satisfied by any available UAV).");
        }

        ScoredUav winner = best.get();
        Uav uav = winner.uav;

        Assignment assignment = new Assignment();
        assignment.setUav(uav);
        assignment.setMission(mission);
        assignment.setEstimatedDistanceKm(winner.distanceKm);
        assignment.setEstimatedBatteryUsage(winner.batteryUsagePercent);
        assignment.setAssignmentScore(winner.score);
        assignment.setStatus(AssignmentStatus.PLANNED);

        assignmentRepository.save(assignment);

        uav.setStatus(UavStatus.ASSIGNED);
        uavRepository.save(uav);

        mission.setStatus(MissionStatus.ASSIGNED);
        missionRepository.save(mission);

        missionLogRepository.save(new MissionLog(mission,
                String.format("Auto-assigned UAV '%s' (score=%.2f, distance=%.1fkm, battery use=%.1f%%)",
                        uav.getName(), winner.score, winner.distanceKm, winner.batteryUsagePercent)));

        return toDto(assignment);
    }

    /**
     * Computes the composite score for a single UAV/mission pair plus whether
     * it is hard-eligible. Lower score = better candidate.
     */
    private ScoredUav scoreUav(Uav uav, Mission mission) {
        double distanceKm = GeoUtils.haversineDistanceKm(
                uav.getLatitude(), uav.getLongitude(),
                mission.getLatitude(), mission.getLongitude());

        // Round trip: to the mission area and back to base.
        double roundTripKm = distanceKm * 2;

        // Battery consumption modeled as a linear function of distance vs. max range.
        double batteryUsagePercent = uav.getMaxRangeKm() > 0
                ? (roundTripKm / uav.getMaxRangeKm()) * 100.0
                : Double.MAX_VALUE;

        boolean rangeOk = uav.getMaxRangeKm() >= mission.getRequiredRangeKm()
                && uav.getMaxRangeKm() >= roundTripKm;
        boolean batteryOk = (uav.getBatteryLevel() - batteryUsagePercent) >= MIN_BATTERY_RESERVE_PERCENT;
        boolean availableOk = uav.getStatus() == UavStatus.AVAILABLE;

        boolean eligible = rangeOk && batteryOk && availableOk;

        double distanceScore = distanceKm;
        double batteryScore = 100.0 - uav.getBatteryLevel();
        double availabilityScore = availableOk ? 0.0 : UNAVAILABLE_PENALTY;
        double priorityScore = -priorityWeight(mission.getPriority());

        double score = distanceScore + batteryScore + availabilityScore + priorityScore;

        ScoredUav scored = new ScoredUav();
        scored.uav = uav;
        scored.distanceKm = distanceKm;
        scored.batteryUsagePercent = batteryUsagePercent;
        scored.score = score;
        scored.eligible = eligible;
        return scored;
    }

    /**
     * Higher priority missions get a small score bonus so that, all else being
     * roughly equal, the best-positioned UAV is preferred for urgent missions.
     */
    private double priorityWeight(MissionPriority priority) {
        return switch (priority) {
            case LOW -> 0.0;
            case MEDIUM -> 5.0;
            case HIGH -> 12.0;
            case CRITICAL -> 25.0;
        };
    }

    private AssignmentResultDto toDto(Assignment assignment) {
        AssignmentResultDto dto = new AssignmentResultDto();
        dto.setAssignmentId(assignment.getId());
        dto.setUavId(assignment.getUav().getId());
        dto.setUavName(assignment.getUav().getName());
        dto.setMissionId(assignment.getMission().getId());
        dto.setMissionTitle(assignment.getMission().getTitle());
        dto.setEstimatedDistanceKm(assignment.getEstimatedDistanceKm());
        dto.setEstimatedBatteryUsage(assignment.getEstimatedBatteryUsage());
        dto.setAssignmentScore(assignment.getAssignmentScore());
        dto.setStatus(assignment.getStatus());
        dto.setAssignedAt(assignment.getAssignedAt());
        return dto;
    }

    private static class ScoredUav {
        Uav uav;
        double distanceKm;
        double batteryUsagePercent;
        double score;
        boolean eligible;
    }
}
