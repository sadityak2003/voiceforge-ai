package com.voiceforge.voice_service.client.impl;

import com.voiceforge.voice_service.client.UserServiceClient;
import com.voiceforge.voice_service.dto.request.UpdateCreditsRequest;
import com.voiceforge.voice_service.dto.response.CreditsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class UserServiceClientImpl implements UserServiceClient {

    private final WebClient webClient;

    //private static final String USER_SERVICE ="http://localhost:8080/api/users";
    @Value("${services.user.url}")
    private String USER_SERVICE_URL;

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceClientImpl.class);

    @Override
    public CreditsResponse getCredits(String token) {

        return webClient.method(HttpMethod.GET)
                .uri(USER_SERVICE_URL + "/credits")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(CreditsResponse.class)
                .block();
    }

    @Override
    public void deductCredits(String token,
                              UpdateCreditsRequest request) {

        webClient.method(HttpMethod.PATCH)
                .uri(USER_SERVICE_URL + "/credits")
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();

        log.info("Deducted {} credits successfully.", request.getCredits());
        //System.out.println("Credits deducted successfully");
    }
}
