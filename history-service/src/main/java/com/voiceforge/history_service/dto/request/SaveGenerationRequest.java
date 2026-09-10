package com.voiceforge.history_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveGenerationRequest {

    @NotBlank
    private String userEmail;

    @NotBlank
    private String text;

    @NotBlank
    private String voiceId;

    @NotBlank
    private String audioUrl;

    @NotNull
    private Integer creditsUsed;
}
