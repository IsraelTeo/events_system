package com.proof.events_system.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class EventDTO {

    @NotBlank(message = "The title cannot be null or blank")
    @Size(max = 100, message = "The title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "The description cannot be null or blank")
    @Size(max = 255, message = "The description cannot exceed 255 characters")
    private String description;

    @NotBlank(message = "The location cannot be null or blank")
    @Size(max = 100, message = "The location cannot exceed 100 characters")
    private String location;

    @NotNull(message = "The capacity cannot be null")
    @Min(value = 1, message = "The capacity must be at least 1")
    private int capacity;

    @NotNull(message = "The date and time cannot be null")
    @Future(message = "The date and time must be in the future")
    private LocalDateTime dateTime;

    public EventDTO() {
    }

    public EventDTO(String title, String description, String location, int capacity, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.capacity = capacity;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
