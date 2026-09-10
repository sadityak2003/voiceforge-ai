package com.voiceforge.authservice.controller;


import com.voiceforge.authservice.dto.request.LoginRequest;
import com.voiceforge.authservice.dto.request.RegisterRequest;
import com.voiceforge.authservice.dto.response.LoginResponse;
import com.voiceforge.authservice.dto.response.RegisterResponse;
import com.voiceforge.authservice.dto.response.UserResponse;
import com.voiceforge.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Authorization endpoints")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a user")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Get current user")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
