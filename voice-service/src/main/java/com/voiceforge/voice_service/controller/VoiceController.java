package com.voiceforge.voice_service.controller;

import com.voiceforge.voice_service.dto.request.GenerateVoiceRequest;
import com.voiceforge.voice_service.dto.response.GenerateVoiceResponse;
import com.voiceforge.voice_service.service.VoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
@Tag(name = "Voice API", description = "Voice generation endpoints")
public class VoiceController {

    private final VoiceService voiceService;

    @Operation(summary = "Generate voice from text")
    @PostMapping("/generate")
    public ResponseEntity<GenerateVoiceResponse> generateVoice(
            @Parameter(hidden = true)
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Valid @RequestBody GenerateVoiceRequest request
            ) throws IOException {

        return ResponseEntity.ok(
                voiceService.generateVoice(token, request)
        );
    }
}
