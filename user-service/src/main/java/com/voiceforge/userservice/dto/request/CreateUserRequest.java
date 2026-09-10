package com.voiceforge.userservice.dto.request;

import com.voiceforge.userservice.entity.AuthProvider;
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