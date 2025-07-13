package com.proof.events_system.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class EventsException extends RuntimeException {

    private HttpStatus status;

    private String description;

    private List<String> reasons;

    public EventsException(ApiError error) {
        this.status = error.getStatus();
        this.description = error.getMessage();
    }

    public EventsException() {
    }

    public EventsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }
}
