package com.uavfleet.repository;

import com.uavfleet.entity.Assignment;
import com.uavfleet.entity.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByUavId(Long uavId);
    Optional<Assignment> findByMissionId(Long missionId);

    /**
     * Eagerly fetches the UAV and Mission associations so callers outside an
     * active transaction (e.g. a read-only controller method) can safely read
     * them without hitting a LazyInitializationException.
     */
    @Query("SELECT a FROM Assignment a JOIN FETCH a.uav JOIN FETCH a.mission WHERE a.status IN :statuses")
    List<Assignment> findByStatusInWithUavAndMission(@Param("statuses") Collection<AssignmentStatus> statuses);
}
