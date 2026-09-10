package com.voiceforge.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {

    private UUID id;

    private String fullName;

    private String email;

    private String role;

    private String message;
}
