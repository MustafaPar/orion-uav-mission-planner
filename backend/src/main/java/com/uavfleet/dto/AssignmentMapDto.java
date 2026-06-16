package com.uavfleet.dto;

/**
 * Lightweight projection used to draw UAV -> Mission route lines on the map.
 */
public class AssignmentMapDto {

    private Long assignmentId;
    private Long uavId;
    private String uavName;
    private Double uavLatitude;
    private Double uavLongitude;
    private Long missionId;
    private String missionTitle;
    private Double missionLatitude;
    private Double missionLongitude;
    private String status;

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

    public Double getUavLatitude() {
        return uavLatitude;
    }

    public void setUavLatitude(Double uavLatitude) {
        this.uavLatitude = uavLatitude;
    }

    public Double getUavLongitude() {
        return uavLongitude;
    }

    public void setUavLongitude(Double uavLongitude) {
        this.uavLongitude = uavLongitude;
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

    public Double getMissionLatitude() {
        return missionLatitude;
    }

    public void setMissionLatitude(Double missionLatitude) {
        this.missionLatitude = missionLatitude;
    }

    public Double getMissionLongitude() {
        return missionLongitude;
    }

    public void setMissionLongitude(Double missionLongitude) {
        this.missionLongitude = missionLongitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
