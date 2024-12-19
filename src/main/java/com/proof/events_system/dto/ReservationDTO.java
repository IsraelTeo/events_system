package com.proof.events_system.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ReservationDTO {
    @NotNull(message = "The user cannot be null")
    @Valid
    private UserEntityDTO userEntityDTO;

    @NotNull(message = "The event cannot be null")
    @Valid
    private EventDTO eventDTO;

    @NotNull(message = "The confirmation status cannot be null")
    private Boolean confirmation;

    public ReservationDTO() {
    }

    public ReservationDTO(UserEntityDTO userEntityDTO, EventDTO eventDTO, Boolean confirmation) {
        this.userEntityDTO = userEntityDTO;
        this.eventDTO = eventDTO;
        this.confirmation = confirmation;
    }

    public UserEntityDTO getUserEntityDTO() {
        return userEntityDTO;
    }

    public void setUserEntityDTO(UserEntityDTO userEntityDTO) {
        this.userEntityDTO = userEntityDTO;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public void setEventDTO (EventDTO eventDTO) {
        this.eventDTO = eventDTO;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }
}
