package com.uavfleet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "uavs")
public class Uav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String model;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Min(0)
    @Max(100)
    @Column(name = "battery_level", nullable = false)
    private Double batteryLevel = 100.0;

    @Positive
    @Column(name = "max_range_km", nullable = false)
    private Double maxRangeKm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UavStatus status = UavStatus.AVAILABLE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Uav() {
    }

    public Uav(String name, String model, Double latitude, Double longitude,
                Double batteryLevel, Double maxRangeKm, UavStatus status) {
        this.name = name;
        this.model = model;
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryLevel = batteryLevel;
        this.maxRangeKm = maxRangeKm;
        this.status = status;
    }

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
