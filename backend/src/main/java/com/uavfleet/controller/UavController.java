package com.uavfleet.controller;

import com.uavfleet.dto.UavDto;
import com.uavfleet.service.UavService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uavs")
@Tag(name = "UAV Management", description = "CRUD operations for fleet UAVs")
public class UavController {

    private final UavService uavService;

    public UavController(UavService uavService) {
        this.uavService = uavService;
    }

    @GetMapping
    public List<UavDto> getAll() {
        return uavService.findAll();
    }

    @GetMapping("/{id}")
    public UavDto getById(@PathVariable Long id) {
        return uavService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UavDto create(@Valid @RequestBody UavDto dto) {
        return uavService.create(dto);
    }

    @PutMapping("/{id}")
    public UavDto update(@PathVariable Long id, @Valid @RequestBody UavDto dto) {
        return uavService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uavService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
