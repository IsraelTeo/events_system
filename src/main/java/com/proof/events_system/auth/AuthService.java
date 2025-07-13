package com.proof.events_system.auth;

import com.proof.events_system.exception.ApiError;
import com.proof.events_system.exception.EventsException;
import com.proof.events_system.payload.response.AuthResponse;
import com.proof.events_system.payload.request.LoginRequest;
import com.proof.events_system.util.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthService(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = this.getAuthentication(loginRequest.email(), loginRequest.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.createToken(authentication);

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        return new AuthResponse("Authentication successfully", token, authentication.getName(), role);
    }

    @Override
    public Authentication getAuthentication(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (userDetails == null) {
            LOGGER.error("User not found");
            throw new EventsException(ApiError.BAD_CREDENTIALS);
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword()) || !userDetails.getUsername().equals(email)) {
            LOGGER.error("Email or password incorrect");
            throw new EventsException(ApiError.BAD_CREDENTIALS);
        }

        return new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
    }
}
