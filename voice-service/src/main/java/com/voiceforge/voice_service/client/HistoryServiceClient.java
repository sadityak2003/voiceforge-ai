package com.voiceforge.voice_service.client;

import com.voiceforge.voice_service.dto.request.SaveGenerationRequest;

public interface HistoryServiceClient {
    void saveGeneration(String token, SaveGenerationRequest request);
}
