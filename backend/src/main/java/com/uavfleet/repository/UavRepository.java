package com.uavfleet.repository;

import com.uavfleet.entity.Uav;
import com.uavfleet.entity.UavStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UavRepository extends JpaRepository<Uav, Long> {
    List<Uav> findByStatus(UavStatus status);
}
