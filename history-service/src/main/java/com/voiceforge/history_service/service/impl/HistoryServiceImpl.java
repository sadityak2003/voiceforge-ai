package com.voiceforge.history_service.service.impl;

import com.voiceforge.history_service.dto.request.SaveGenerationRequest;
import com.voiceforge.history_service.dto.response.GenerationResponse;
import com.voiceforge.history_service.entity.Generation;
import com.voiceforge.history_service.repository.GenerationRepository;
import com.voiceforge.history_service.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final GenerationRepository generationRepository;

    @Override
    public GenerationResponse saveGeneration(SaveGenerationRequest request) {

        Generation generation = Generation.builder()
                .userEmail(request.getUserEmail())
                .text(request.getText())
                .voiceId(request.getVoiceId())
                .audioUrl(request.getAudioUrl())
                .creditsUsed(request.getCreditsUsed())
                .build();

        generation = generationRepository.save(generation);

        return mapToResponse(generation);
    }

    @Override
    public List<GenerationResponse> getHistory() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication = {}", authentication);
        log.info("Principal = {}", authentication.getPrincipal());
        log.info("Name = {}", authentication.getName());

        String email = authentication.getName();

        return generationRepository
                .findByUserEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private GenerationResponse mapToResponse(Generation generation) {

        return GenerationResponse.builder()
                .id(generation.getId())
                .text(generation.getText())
                .voiceId(generation.getVoiceId())
                .audioUrl(generation.getAudioUrl())
                .creditsUsed(generation.getCreditsUsed())
                .createdAt(generation.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public void deleteHistory(UUID id) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        generationRepository.deleteByIdAndUserEmail(id, email);
    }
}
