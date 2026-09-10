package com.voiceforge.history_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationResponse {

    private UUID id;

    private String text;

    private String voiceId;

    private String audioUrl;

    private Integer creditsUsed;

    private LocalDateTime createdAt;
}
