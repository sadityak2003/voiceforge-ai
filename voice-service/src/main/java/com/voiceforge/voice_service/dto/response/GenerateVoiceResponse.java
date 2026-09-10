package com.voiceforge.voice_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenerateVoiceResponse {

    private String message;

    private String audioUrl;
}
