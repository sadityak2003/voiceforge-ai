package com.voiceforge.voice_service.service.impl;

import com.voiceforge.voice_service.config.ElevenLabsProperties;
import com.voiceforge.voice_service.service.ElevenLabsService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ElevenLabsServiceImpl implements ElevenLabsService {

    private final WebClient webClient;
    private final ElevenLabsProperties properties;

    @Override
    public byte[] generateSpeech(String text, String voiceId) {

        String url = properties.getBaseUrl()
                + "/v1/text-to-speech/"
                + voiceId;

        String body = """
                {
                  "text":"%s",
                  "model_id":"eleven_multilingual_v2"
                }
                """.formatted(text);

        ByteArrayResource resource = webClient.post()
                .uri(url)
                .header("xi-api-key", properties.getApiKey())
                .header(HttpHeaders.ACCEPT, "audio/mpeg")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .block();

        return resource.getByteArray();
    }
}
