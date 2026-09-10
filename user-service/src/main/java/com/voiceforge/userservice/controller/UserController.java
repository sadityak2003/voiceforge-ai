package com.voiceforge.userservice.controller;

import com.voiceforge.userservice.dto.request.CreateUserRequest;
import com.voiceforge.userservice.dto.request.UpdateCreditsRequest;
import com.voiceforge.userservice.dto.request.UpdateProfileRequest;
import com.voiceforge.userservice.dto.response.CreditsResponse;
import com.voiceforge.userservice.dto.response.UserResponse;
import com.voiceforge.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "User details")
public class UserController {

    private final UserService userService;

    @Operation(summary = "User details")
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(summary = "Update user details")
    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request
            ) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }

    @Operation(summary = "Remaining credits")
    @GetMapping("/credits")
    public ResponseEntity<CreditsResponse> getCredits() {
        return ResponseEntity.ok(userService.getCredits());
    }

    @Operation(summary = "Update credits")
    @PatchMapping("/credits")
    public ResponseEntity<CreditsResponse> updateCredits(
            @Valid @RequestBody UpdateCreditsRequest request
            ) {
        return ResponseEntity.ok(userService.updateCredits(request));
    }

    @PostMapping("/internal")
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userService.createUser(request));
    }
}
