package com.proof.events_system.payload.response;

public record AuthResponse(String message,
                           String token,
                           String email,
                           String role) {
}
