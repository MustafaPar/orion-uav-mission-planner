package com.uavfleet.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "mission_logs")
public class MissionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(nullable = false)
    private String message;

    @Column(name = "logged_at", nullable = false, updatable = false)
    private Instant loggedAt = Instant.now();

    public MissionLog() {
    }

    public MissionLog(Mission mission, String message) {
        this.mission = mission;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(Instant loggedAt) {
        this.loggedAt = loggedAt;
    }
}
