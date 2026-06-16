package com.uavfleet.repository;

import com.uavfleet.entity.Mission;
import com.uavfleet.entity.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByStatus(MissionStatus status);
}
