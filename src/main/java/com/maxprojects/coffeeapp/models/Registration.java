package com.maxprojects.coffeeapp.models;


import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic identity for discount check
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    // Contact info (optional depending on opt-in)
    private String phone;

    private String email;

    // Notification preferences
    private boolean smsOptIn;
    private boolean emailOptIn;

    private boolean emailNotificationSent;
    private boolean smsNotificationSent;

    // Derived from phone number (+33 / +32)
    private boolean smsEligible;

    // Discount usage at the counter
    private boolean used = false;

    private LocalDate eventDate;

    private boolean arrived = false;



    @ManyToOne
    private Event event;

    public Registration() {}

    // Optional constructor if you want one
    public Registration(String firstName, String lastName, String phone, String email,
                        boolean smsOptIn, boolean emailOptIn, boolean smsEligible, Event event) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.smsOptIn = smsOptIn;
        this.emailOptIn = emailOptIn;
        this.smsEligible = smsEligible;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    // --- Getters & Setters ---

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSmsOptIn() {
        return smsOptIn;
    }

    public void setSmsOptIn(boolean smsOptIn) {
        this.smsOptIn = smsOptIn;
    }

    public boolean isEmailOptIn() {
        return emailOptIn;
    }

    public void setEmailOptIn(boolean emailOptIn) {
        this.emailOptIn = emailOptIn;
    }

    public boolean isSmsEligible() {
        return smsEligible;
    }

    public void setSmsEligible(boolean smsEligible) {
        this.smsEligible = smsEligible;
    }

    public boolean isEmailNotificationSent() {
        return emailNotificationSent;
    }

    public void setEmailNotificationSent(boolean emailNotificationSent) {
        this.emailNotificationSent = emailNotificationSent;
    }

    public boolean isSmsNotificationSent() {
        return smsNotificationSent;
    }

    public void setSmsNotificationSent(boolean smsNotificationSent) {
        this.smsNotificationSent = smsNotificationSent;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isArrived() { return arrived; }
    public void setArrived(boolean arrived) { this.arrived = arrived; }


}
