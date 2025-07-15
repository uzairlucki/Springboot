package com.dps.crud.controller;

import com.dps.crud.model.User;
import com.dps.crud.payload.LoginRequest;
import com.dps.crud.payload.LoginResponse;
import com.dps.crud.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@CrossOrigin(origins = {"http://localhost:5171", "http://localhost:3000"}, allowCredentials = "true")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        log.info("Attempting to register new user: {}", newUser.getUsername());
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            log.warn("Registration failed: Username '{}' already exists.", newUser.getUsername());
            return new ResponseEntity<>(new LoginResponse("Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (newUser.getRole() == null || newUser.getRole().isEmpty()) {
            newUser.setRole("USER");
        } else {
             newUser.setRole(newUser.getRole().toUpperCase());
        }

        userRepository.save(newUser);
        log.info("User '{}' registered successfully.", newUser.getUsername());
        return new ResponseEntity<>(new LoginResponse("User registered successfully!"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        log.info("Authentication attempt for user: {}", loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("User '{}' authenticated successfully. Session established.", loginRequest.getUsername());
            return ResponseEntity.ok(new LoginResponse("User logged in successfully!"));

        } catch (Exception e) {
            log.error("Authentication failed for user {}: {}", loginRequest.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid Username or Password!"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        log.info("Attempting to logout user.");
        try {
            // Invalidate the current session
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Invalidate the session
                log.info("Session invalidated for user.");
            }
            // Clear Spring Security context
            SecurityContextHolder.clearContext();
            log.info("Security context cleared.");

            return ResponseEntity.ok(new LoginResponse("Logged out successfully!"));
        } catch (Exception e) {
            log.error("Error during logout: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse("Logout failed."));
        }
    }
}