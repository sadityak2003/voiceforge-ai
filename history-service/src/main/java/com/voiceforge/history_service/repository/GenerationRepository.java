package com.voiceforge.history_service.repository;

import com.voiceforge.history_service.entity.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GenerationRepository extends JpaRepository<Generation, UUID> {
    List<Generation> findByUserEmailOrderByCreatedAtDesc(String userEmail);

    void deleteByIdAndUserEmail(UUID id, String userEmail);
}
