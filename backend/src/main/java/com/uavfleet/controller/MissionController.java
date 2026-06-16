package com.uavfleet.controller;

import com.uavfleet.dto.AssignmentResultDto;
import com.uavfleet.dto.MissionDto;
import com.uavfleet.service.AssignmentService;
import com.uavfleet.service.MissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@Tag(name = "Mission Management", description = "CRUD operations for missions and auto-assignment")
public class MissionController {

    private final MissionService missionService;
    private final AssignmentService assignmentService;

    public MissionController(MissionService missionService, AssignmentService assignmentService) {
        this.missionService = missionService;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<MissionDto> getAll() {
        return missionService.findAll();
    }

    @GetMapping("/{id}")
    public MissionDto getById(@PathVariable Long id) {
        return missionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MissionDto create(@Valid @RequestBody MissionDto dto) {
        return missionService.create(dto);
    }

    @PutMapping("/{id}")
    public MissionDto update(@PathVariable Long id, @Valid @RequestBody MissionDto dto) {
        return missionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        missionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Triggers the auto-assignment algorithm: finds the best available UAV
     * for this mission based on distance, battery, range and priority.
     */
    @PostMapping("/{id}/assign")
    public AssignmentResultDto assign(@PathVariable Long id) {
        return assignmentService.assignBestUav(id);
    }
}
