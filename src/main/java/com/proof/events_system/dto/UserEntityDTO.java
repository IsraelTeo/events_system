package com.proof.events_system.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserEntityDTO {

    @NotBlank(message = "The first name cannot be blank")
    @Size(max = 50, message = "The first name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "The last name cannot be blank")
    @Size(max = 70, message = "The last name cannot exceed 70 characters")
    private String lastName;

    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "The password cannot be blank")
    private String password;

    @NotNull(message = "The role cannot be null")
    @Valid
    private RoleEntityDTO role;

    public UserEntityDTO() {
    }

    public UserEntityDTO(String firstName, String lastName, String email, String password, RoleEntityDTO role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEntityDTO getRole() {
        return role;
    }

    public void setRole(RoleEntityDTO role) {
        this.role = role;
    }
}
