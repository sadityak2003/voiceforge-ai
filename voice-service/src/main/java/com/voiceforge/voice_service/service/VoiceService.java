package com.voiceforge.voice_service.service;

import com.voiceforge.voice_service.dto.request.GenerateVoiceRequest;
import com.voiceforge.voice_service.dto.response.GenerateVoiceResponse;

import java.io.IOException;

public interface VoiceService {

    GenerateVoiceResponse generateVoice(
            String token,
            GenerateVoiceRequest request
    ) throws IOException;
}
