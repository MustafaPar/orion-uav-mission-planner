package com.uavfleet.service;

import com.uavfleet.dto.DashboardStatsDto;
import com.uavfleet.entity.MissionStatus;
import com.uavfleet.entity.Uav;
import com.uavfleet.entity.UavStatus;
import com.uavfleet.repository.MissionRepository;
import com.uavfleet.repository.UavRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final UavRepository uavRepository;
    private final MissionRepository missionRepository;

    public DashboardService(UavRepository uavRepository, MissionRepository missionRepository) {
        this.uavRepository = uavRepository;
        this.missionRepository = missionRepository;
    }

    public DashboardStatsDto getStats() {
        List<Uav> uavs = uavRepository.findAll();

        long total = uavs.size();
        long available = uavs.stream().filter(u -> u.getStatus() == UavStatus.AVAILABLE).count();
        double avgBattery = uavs.stream().mapToDouble(Uav::getBatteryLevel).average().orElse(0.0);

        long activeMissions = missionRepository.findByStatus(MissionStatus.ASSIGNED).size()
                + missionRepository.findByStatus(MissionStatus.IN_PROGRESS).size();
        long completedMissions = missionRepository.findByStatus(MissionStatus.COMPLETED).size();

        DashboardStatsDto dto = new DashboardStatsDto();
        dto.setTotalUavs(total);
        dto.setAvailableUavs(available);
        dto.setActiveMissions(activeMissions);
        dto.setCompletedMissions(completedMissions);
        dto.setAverageBatteryLevel(Math.round(avgBattery * 10) / 10.0);
        return dto;
    }
}
