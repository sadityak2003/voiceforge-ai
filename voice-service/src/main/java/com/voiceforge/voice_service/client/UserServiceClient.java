package com.voiceforge.voice_service.client;

import com.voiceforge.voice_service.dto.request.UpdateCreditsRequest;
import com.voiceforge.voice_service.dto.response.CreditsResponse;

public interface UserServiceClient {

    CreditsResponse getCredits(String token);

    void deductCredits(String token, UpdateCreditsRequest request);
}
