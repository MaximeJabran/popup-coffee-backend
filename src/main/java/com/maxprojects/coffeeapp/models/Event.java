package com.maxprojects.coffeeapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Automatically computed: startDate minus 48 hours
    private LocalDateTime earlyBirdDeadline;

    private String location;

    private boolean qrEmailsSent;

    public Event() {}

    public Event(LocalDateTime startDate, LocalDateTime endDate, String location) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.earlyBirdDeadline = startDate.minusHours(48);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        this.earlyBirdDeadline = startDate.minusHours(48);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEarlyBirdDeadline() {
        return earlyBirdDeadline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
