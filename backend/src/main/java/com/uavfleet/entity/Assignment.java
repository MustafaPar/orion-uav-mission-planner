package com.uavfleet.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uav_id", nullable = false)
    private Uav uav;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(name = "estimated_distance_km", nullable = false)
    private Double estimatedDistanceKm;

    @Column(name = "estimated_battery_usage", nullable = false)
    private Double estimatedBatteryUsage;

    @Column(name = "assignment_score", nullable = false)
    private Double assignmentScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status = AssignmentStatus.PLANNED;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    private Instant assignedAt = Instant.now();

    public Assignment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uav getUav() {
        return uav;
    }

    public void setUav(Uav uav) {
        this.uav = uav;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Double getEstimatedDistanceKm() {
        return estimatedDistanceKm;
    }

    public void setEstimatedDistanceKm(Double estimatedDistanceKm) {
        this.estimatedDistanceKm = estimatedDistanceKm;
    }

    public Double getEstimatedBatteryUsage() {
        return estimatedBatteryUsage;
    }

    public void setEstimatedBatteryUsage(Double estimatedBatteryUsage) {
        this.estimatedBatteryUsage = estimatedBatteryUsage;
    }

    public Double getAssignmentScore() {
        return assignmentScore;
    }

    public void setAssignmentScore(Double assignmentScore) {
        this.assignmentScore = assignmentScore;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }
}
