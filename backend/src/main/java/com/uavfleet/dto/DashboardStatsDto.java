package com.uavfleet.dto;

public class DashboardStatsDto {

    private long totalUavs;
    private long availableUavs;
    private long activeMissions;
    private long completedMissions;
    private double averageBatteryLevel;

    public long getTotalUavs() {
        return totalUavs;
    }

    public void setTotalUavs(long totalUavs) {
        this.totalUavs = totalUavs;
    }

    public long getAvailableUavs() {
        return availableUavs;
    }

    public void setAvailableUavs(long availableUavs) {
        this.availableUavs = availableUavs;
    }

    public long getActiveMissions() {
        return activeMissions;
    }

    public void setActiveMissions(long activeMissions) {
        this.activeMissions = activeMissions;
    }

    public long getCompletedMissions() {
        return completedMissions;
    }

    public void setCompletedMissions(long completedMissions) {
        this.completedMissions = completedMissions;
    }

    public double getAverageBatteryLevel() {
        return averageBatteryLevel;
    }

    public void setAverageBatteryLevel(double averageBatteryLevel) {
        this.averageBatteryLevel = averageBatteryLevel;
    }
}
