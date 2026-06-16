package com.uavfleet.dto;

import com.uavfleet.entity.UavStatus;
import jakarta.validation.constraints.*;

public class UavDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String model;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Min(0)
    @Max(100)
    private Double batteryLevel;

    @Positive
    private Double maxRangeKm;

    private UavStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public Double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Double getMaxRangeKm() {
        return maxRangeKm;
    }

    public void setMaxRangeKm(Double maxRangeKm) {
        this.maxRangeKm = maxRangeKm;
    }

    public UavStatus getStatus() {
        return status;
    }

    public void setStatus(UavStatus status) {
        this.status = status;
    }
}
