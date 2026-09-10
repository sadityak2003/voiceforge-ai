package com.voiceforge.voice_service.client.impl;

import com.voiceforge.voice_service.client.HistoryServiceClient;
import com.voiceforge.voice_service.dto.request.SaveGenerationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class HistoryServiceClientImpl implements HistoryServiceClient {

    private final WebClient webClient;
    //private static final String HISTORY_SERVICE = "http://localhost:8080/api/history";

    @Value("${services.history.url}")
    private String HISTORY_SERVICE_URL;

    private static final Logger log =
            LoggerFactory.getLogger(HistoryServiceClientImpl.class);

    @Override
    public void saveGeneration(String token, SaveGenerationRequest request) {

        webClient.method(HttpMethod.POST)
                .uri(HISTORY_SERVICE_URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();

        log.info("History saved successfully for user {}", request.getUserEmail());
        //System.out.println("History saved successfully");
    }
}
