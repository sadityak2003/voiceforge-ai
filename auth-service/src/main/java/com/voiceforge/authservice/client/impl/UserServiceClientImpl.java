package com.voiceforge.authservice.client.impl;

import com.voiceforge.authservice.client.UserServiceClient;
import com.voiceforge.authservice.dto.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserServiceClientImpl implements UserServiceClient {

    private final WebClient webClient;

    @Value("${services.user.url}")
    private String USER_SERVICE_URL;

    @Override
    public void createUser(CreateUserRequest request) {

        webClient.method(HttpMethod.POST)
                .uri(USER_SERVICE_URL + "/internal")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
