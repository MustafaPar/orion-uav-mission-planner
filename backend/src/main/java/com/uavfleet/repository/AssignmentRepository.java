package com.uavfleet.repository;

import com.uavfleet.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByUavId(Long uavId);
    Optional<Assignment> findByMissionId(Long missionId);
}
