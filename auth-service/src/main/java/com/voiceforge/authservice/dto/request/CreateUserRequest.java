package com.voiceforge.authservice.dto.request;

import com.voiceforge.authservice.entity.AuthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    private AuthProvider authProvider;
}