package com.proof.events_system.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank(message = "The field is required.")
                           @Email(message = "The email is not valid.")
                           String email,

                           @Size(min = 8, message = "The field must have at least 8 characters.")
                           @NotBlank(message = "The field is required.")
                           String password) {
}
