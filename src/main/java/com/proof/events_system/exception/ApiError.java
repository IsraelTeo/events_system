package com.proof.events_system.exception;

import org.springframework.http.HttpStatus;

public enum ApiError {

    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Event not found"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Role not found"),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Reservation not found"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    ;

    private final HttpStatus status;

    private final String message;

    ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
