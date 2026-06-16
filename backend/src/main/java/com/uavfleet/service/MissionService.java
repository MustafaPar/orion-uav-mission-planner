package com.uavfleet.service;

import com.uavfleet.dto.MissionDto;
import com.uavfleet.entity.Mission;
import com.uavfleet.entity.MissionStatus;
import com.uavfleet.exception.ResourceNotFoundException;
import com.uavfleet.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
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

    @Transactional
    public void delete(Long id) {
        missionRepository.delete(getEntity(id));
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
