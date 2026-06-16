package com.uavfleet.service;

import com.uavfleet.dto.MissionDto;
import com.uavfleet.entity.Mission;
import com.uavfleet.entity.MissionStatus;
import com.uavfleet.entity.Uav;
import com.uavfleet.entity.UavStatus;
import com.uavfleet.exception.ResourceNotFoundException;
import com.uavfleet.repository.AssignmentRepository;
import com.uavfleet.repository.MissionLogRepository;
import com.uavfleet.repository.MissionRepository;
import com.uavfleet.repository.UavRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UavRepository uavRepository;
    private final MissionLogRepository missionLogRepository;

    public MissionService(MissionRepository missionRepository,
                           AssignmentRepository assignmentRepository,
                           UavRepository uavRepository,
                           MissionLogRepository missionLogRepository) {
        this.missionRepository = missionRepository;
        this.assignmentRepository = assignmentRepository;
        this.uavRepository = uavRepository;
        this.missionLogRepository = missionLogRepository;
    }

    public List<MissionDto> findAll() {
        return missionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public MissionDto findById(Long id) {
        return toDto(getEntity(id));
    }

    @Transactional
    public MissionDto create(MissionDto dto) {
        Mission mission = new Mission();
        applyDto(mission, dto);
        return toDto(missionRepository.save(mission));
    }

    @Transactional
    public MissionDto update(Long id, MissionDto dto) {
        Mission mission = getEntity(id);
        applyDto(mission, dto);
        return toDto(missionRepository.save(mission));
    }

    /**
     * Deleting a mission also removes its assignment and mission logs (if any)
     * and frees the UAV back to AVAILABLE, instead of failing with a raw
     * foreign-key constraint error from the database.
     */
    @Transactional
    public void delete(Long id) {
        Mission mission = getEntity(id);

        assignmentRepository.findByMissionId(id).ifPresent(assignment -> {
            Uav uav = assignment.getUav();
            if (uav.getStatus() == UavStatus.ASSIGNED) {
                uav.setStatus(UavStatus.AVAILABLE);
                uavRepository.save(uav);
            }
            assignmentRepository.delete(assignment);
        });

        missionLogRepository.deleteAll(missionLogRepository.findByMissionIdOrderByLoggedAtDesc(id));

        missionRepository.delete(mission);
    }

    private Mission getEntity(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found: " + id));
    }

    private void applyDto(Mission mission, MissionDto dto) {
        mission.setTitle(dto.getTitle());
        mission.setLatitude(dto.getLatitude());
        mission.setLongitude(dto.getLongitude());
        mission.setPriority(dto.getPriority());
        mission.setType(dto.getType());
        mission.setRequiredRangeKm(dto.getRequiredRangeKm());
        mission.setEstimatedDurationMinutes(dto.getEstimatedDurationMinutes());
        mission.setStatus(dto.getStatus() != null ? dto.getStatus() : MissionStatus.PENDING);
    }

    private MissionDto toDto(Mission mission) {
        MissionDto dto = new MissionDto();
        dto.setId(mission.getId());
        dto.setTitle(mission.getTitle());
        dto.setLatitude(mission.getLatitude());
        dto.setLongitude(mission.getLongitude());
        dto.setPriority(mission.getPriority());
        dto.setType(mission.getType());
        dto.setStatus(mission.getStatus());
        dto.setRequiredRangeKm(mission.getRequiredRangeKm());
        dto.setEstimatedDurationMinutes(mission.getEstimatedDurationMinutes());
        return dto;
    }
}
