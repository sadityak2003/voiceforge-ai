package com.voiceforge.voice_service.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime timeStamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
