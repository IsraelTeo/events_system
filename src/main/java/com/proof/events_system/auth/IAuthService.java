package com.proof.events_system.auth;

import com.proof.events_system.payload.response.AuthResponse;
import com.proof.events_system.payload.request.LoginRequest;
import org.springframework.security.core.Authentication;

public interface IAuthService {

    AuthResponse login(LoginRequest loginRequest);

    Authentication getAuthentication(String email, String password);
}
