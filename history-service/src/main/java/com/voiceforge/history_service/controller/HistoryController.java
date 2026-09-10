package com.voiceforge.history_service.controller;

import com.voiceforge.history_service.dto.request.SaveGenerationRequest;
import com.voiceforge.history_service.dto.response.GenerationResponse;
import com.voiceforge.history_service.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@Tag(name = "History API", description = "Voice generation history")
public class HistoryController {

    private final HistoryService historyService;

    @Operation(summary = "Get generation history")
    @GetMapping
    public ResponseEntity<List<GenerationResponse>> getHistory() {

        return ResponseEntity.ok(
                historyService.getHistory()
        );
    }

    @Operation(summary = "Save generation history")
    @PostMapping
    public ResponseEntity<GenerationResponse> saveGeneration(
            @Valid @RequestBody SaveGenerationRequest request
            ) {

        return ResponseEntity.ok(
                historyService.saveGeneration(request)
        );
    }

    @Operation(summary = "Delete history record")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable UUID id) {

        historyService.deleteHistory(id);

        return ResponseEntity.noContent().build();
    }
}
