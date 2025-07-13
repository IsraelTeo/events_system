package com.proof.events_system.dto;

import jakarta.validation.constraints.NotNull;

public class ReservationDTO {

    @NotNull(message = "The user ID cannot be null")
    private Long userId;

    @NotNull(message = "The event ID cannot be null")
    private Long eventId;

    @NotNull(message = "The confirmation status cannot be null")
    private Boolean confirmation;

    public ReservationDTO() {
    }

    public ReservationDTO(Long userId, Long eventId, Boolean confirmation) {
        this.userId = userId;
        this.eventId = eventId;
        this.confirmation = confirmation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }
}
