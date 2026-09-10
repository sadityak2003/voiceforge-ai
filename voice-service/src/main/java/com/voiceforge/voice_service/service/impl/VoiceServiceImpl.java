package com.voiceforge.voice_service.service.impl;

import com.voiceforge.voice_service.client.HistoryServiceClient;
import com.voiceforge.voice_service.client.UserServiceClient;
import com.voiceforge.voice_service.dto.request.GenerateVoiceRequest;
import com.voiceforge.voice_service.dto.request.SaveGenerationRequest;
import com.voiceforge.voice_service.dto.request.UpdateCreditsRequest;
import com.voiceforge.voice_service.dto.response.CreditsResponse;
import com.voiceforge.voice_service.dto.response.GenerateVoiceResponse;
import com.voiceforge.voice_service.exception.InsufficientCreditsException;
import com.voiceforge.voice_service.exception.VoiceGenerationException;
import com.voiceforge.voice_service.security.JwtService;
import com.voiceforge.voice_service.service.ElevenLabsService;
import com.voiceforge.voice_service.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VoiceServiceImpl implements VoiceService {

    private final ElevenLabsService elevenLabsService;
    private final UserServiceClient userServiceClient;
    private final HistoryServiceClient historyServiceClient;
    private final JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(VoiceServiceImpl.class);

    @Override
    public GenerateVoiceResponse generateVoice(String token,
                                               GenerateVoiceRequest request) throws IOException {

        log.info("Voice generation request received.");

        CreditsResponse creditsResponse =
                userServiceClient.getCredits(token);

        log.info("User has {} credits.", creditsResponse.getCredits());

        if (creditsResponse.getCredits() < 10) {
            log.warn("Voice generation failed due to insufficient credits.");
            throw new InsufficientCreditsException("Insufficient Credits");
        }

        byte[] audio;

        try {

            log.info("Generating speech using ElevenLabs...");

            audio = elevenLabsService.generateSpeech(
                    request.getText(),
                    request.getVoiceId()
            );

            log.info("Speech generated successfully.");

        } catch (Exception e) {

            log.error("Voice generation failed.", e);

            throw new VoiceGenerationException(
                    "Failed to generate voice.",
                    e
            );
        }

        userServiceClient.deductCredits(
                token,
                UpdateCreditsRequest.builder()
                        .credits(-10)
                        .build()
        );

        Path folder = Paths.get("generated-audio");

        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
            log.info("Created generated-audio directory.");
        }

        String fileName = UUID.randomUUID() + ".mp3";

        Path path = folder.resolve(fileName);

        Files.write(path, audio);

        String audioUrl = "http://localhost:8083/audio/" + fileName;

        log.info("Audio file saved successfully at {}", audioUrl);

        String email = jwtService.extractUsername(
                token.substring(7)
        );

        historyServiceClient.saveGeneration(
                token,
                SaveGenerationRequest.builder()
                        .userEmail(email)
                        .text(request.getText())
                        .voiceId(request.getVoiceId())
                        .audioUrl(audioUrl)
                        .creditsUsed(10)
                        .build()
        );

        log.info("Voice generation history saved for user {}", email);

        log.info("Voice generation completed successfully.");

        return GenerateVoiceResponse.builder()
                .message("Voice generated successfully.")
                .audioUrl(audioUrl)
                .build();
    }
}
