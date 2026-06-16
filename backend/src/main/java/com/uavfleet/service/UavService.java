package com.uavfleet.service;

import com.uavfleet.dto.UavDto;
import com.uavfleet.entity.Uav;
import com.uavfleet.entity.UavStatus;
import com.uavfleet.exception.ResourceNotFoundException;
import com.uavfleet.repository.UavRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UavService {

    private final UavRepository uavRepository;

    public UavService(UavRepository uavRepository) {
        this.uavRepository = uavRepository;
    }

    public List<UavDto> findAll() {
        return uavRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UavDto findById(Long id) {
        return toDto(getEntity(id));
    }

    @Transactional
    public UavDto create(UavDto dto) {
        Uav uav = new Uav();
        applyDto(uav, dto);
        return toDto(uavRepository.save(uav));
    }

    @Transactional
    public UavDto update(Long id, UavDto dto) {
        Uav uav = getEntity(id);
        applyDto(uav, dto);
        return toDto(uavRepository.save(uav));
    }

    @Transactional
    public void delete(Long id) {
        Uav uav = getEntity(id);
        uavRepository.delete(uav);
    }

    private Uav getEntity(Long id) {
        return uavRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UAV not found: " + id));
    }

    private void applyDto(Uav uav, UavDto dto) {
        uav.setName(dto.getName());
        uav.setModel(dto.getModel());
        uav.setLatitude(dto.getLatitude());
        uav.setLongitude(dto.getLongitude());
        uav.setBatteryLevel(dto.getBatteryLevel() != null ? dto.getBatteryLevel() : 100.0);
        uav.setMaxRangeKm(dto.getMaxRangeKm());
        uav.setStatus(dto.getStatus() != null ? dto.getStatus() : UavStatus.AVAILABLE);
    }

    private UavDto toDto(Uav uav) {
        UavDto dto = new UavDto();
        dto.setId(uav.getId());
        dto.setName(uav.getName());
        dto.setModel(uav.getModel());
        dto.setLatitude(uav.getLatitude());
        dto.setLongitude(uav.getLongitude());
        dto.setBatteryLevel(uav.getBatteryLevel());
        dto.setMaxRangeKm(uav.getMaxRangeKm());
        dto.setStatus(uav.getStatus());
        return dto;
    }
}
