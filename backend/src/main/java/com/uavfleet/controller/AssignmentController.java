package com.uavfleet.controller;

import com.uavfleet.dto.AssignmentMapDto;
import com.uavfleet.entity.Assignment;
import com.uavfleet.entity.AssignmentStatus;
import com.uavfleet.repository.AssignmentRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Assignments", description = "Read-only view of UAV-to-mission assignments (used for map route lines)")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Returns active (non-terminal) assignments with the UAV/mission coordinates
     * needed to draw a route line between them on the map.
     */
    @GetMapping
    public List<AssignmentMapDto> getActiveAssignments() {
        return assignmentRepository
                .findByStatusInWithUavAndMission(Set.of(AssignmentStatus.PLANNED, AssignmentStatus.ACTIVE))
                .stream()
                .map(this::toMapDto)
                .collect(Collectors.toList());
    }

    private AssignmentMapDto toMapDto(Assignment a) {
        AssignmentMapDto dto = new AssignmentMapDto();
        dto.setAssignmentId(a.getId());
        dto.setUavId(a.getUav().getId());
        dto.setUavName(a.getUav().getName());
        dto.setUavLatitude(a.getUav().getLatitude());
        dto.setUavLongitude(a.getUav().getLongitude());
        dto.setMissionId(a.getMission().getId());
        dto.setMissionTitle(a.getMission().getTitle());
        dto.setMissionLatitude(a.getMission().getLatitude());
        dto.setMissionLongitude(a.getMission().getLongitude());
        dto.setStatus(a.getStatus().name());
        return dto;
    }
}
