package com.uavfleet.dto;

import com.uavfleet.entity.MissionPriority;
import com.uavfleet.entity.MissionStatus;
import com.uavfleet.entity.MissionType;
import jakarta.validation.constraints.*;

public class MissionDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private MissionPriority priority;

    @NotNull
    private MissionType type;

    private MissionStatus status;

    @Positive
    private Double requiredRangeKm;

    @Positive
    private Integer estimatedDurationMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public MissionPriority getPriority() {
        return priority;
    }

    public void setPriority(MissionPriority priority) {
        this.priority = priority;
    }

    public MissionType getType() {
        return type;
    }

    public void setType(MissionType type) {
        this.type = type;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Double getRequiredRangeKm() {
        return requiredRangeKm;
    }

    public void setRequiredRangeKm(Double requiredRangeKm) {
        this.requiredRangeKm = requiredRangeKm;
    }

    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }
}
