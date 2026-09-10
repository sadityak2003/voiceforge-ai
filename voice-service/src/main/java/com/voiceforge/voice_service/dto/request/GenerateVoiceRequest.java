package com.voiceforge.voice_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateVoiceRequest {

    @NotBlank(message = "Text is required")
    private String text;

    @NotBlank(message = "Voice Id is required")
    private String voiceId;
}
