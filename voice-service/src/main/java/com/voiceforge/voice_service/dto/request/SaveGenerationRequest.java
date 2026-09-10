package com.voiceforge.voice_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveGenerationRequest {

    private String userEmail;

    private String text;

    private String voiceId;

    private String audioUrl;

    private Integer creditsUsed;
}
