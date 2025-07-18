package com.proof.events_system.controller;

import com.proof.events_system.dto.UserEntityDTO;
import com.proof.events_system.payload.request.LoginRequest;
import com.proof.events_system.payload.response.AuthResponse;
import com.proof.events_system.payload.response.MessageResponse;
import com.proof.events_system.auth.AuthService;
import com.proof.events_system.service.implement.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getUserById(@Min(1) @PathVariable Long id) {
        LOGGER.info("Getting user by id");
        UserEntityDTO user = userService.getUserById(id);
        MessageResponse messageResponse = new MessageResponse("User found successfully", user);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    public ResponseEntity<MessageResponse> getAllUsers() {
        LOGGER.info("Getting all users");
        List<UserEntityDTO> users = userService.getAllUsers();
        MessageResponse messageResponse = new MessageResponse("Users found successfully", users);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserEntityDTO userDTO) {
        LOGGER.info("Registering user");
        userService.registerUser(userDTO);
        MessageResponse messageResponse = new MessageResponse("User registered successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest user) {
        return ResponseEntity.ok(authService.login(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateUser(@Min(1) @PathVariable Long id, @Valid @RequestBody UserEntityDTO userDTO) {
        LOGGER.info("Updating user");
        userService.updateUser(id, userDTO);
        MessageResponse messageResponse = new MessageResponse("User updated successfully", userDTO);
        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@Min(1) @PathVariable Long id) {
        LOGGER.info("Deleting user");
        userService.deleteUser(id);
        MessageResponse messageResponse = new MessageResponse("User deleted successfully", null);
        return ResponseEntity.ok(messageResponse);
    }
}

