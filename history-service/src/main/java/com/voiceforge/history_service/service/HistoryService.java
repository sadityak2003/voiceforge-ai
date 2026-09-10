package com.voiceforge.history_service.service;

import com.voiceforge.history_service.dto.request.SaveGenerationRequest;
import com.voiceforge.history_service.dto.response.GenerationResponse;

import java.util.List;
import java.util.UUID;

public interface HistoryService {

    GenerationResponse saveGeneration(SaveGenerationRequest request);

    List<GenerationResponse> getHistory();

    void deleteHistory(UUID id);
}
