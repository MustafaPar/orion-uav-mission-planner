package com.uavfleet.dto;

import com.uavfleet.entity.AssignmentStatus;

import java.time.Instant;

/**
 * Response DTO returned after the auto-assignment algorithm picks
 * the best UAV for a mission (or explains why none qualified).
 */
public class AssignmentResultDto {

    private Long assignmentId;
    private Long uavId;
    private String uavName;
    private Long missionId;
    private String missionTitle;
    private Double estimatedDistanceKm;
    private Double estimatedBatteryUsage;
    private Double assignmentScore;
    private AssignmentStatus status;
    private Instant assignedAt;

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getUavId() {
        return uavId;
    }

    public void setUavId(Long uavId) {
        this.uavId = uavId;
    }

    public String getUavName() {
        return uavName;
    }

    public void setUavName(String uavName) {
        this.uavName = uavName;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionTitle() {
        return missionTitle;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
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
