package com.voiceforge.voice_service.exception;

public class VoiceGenerationException extends RuntimeException {

    public VoiceGenerationException(String message) {
        super(message);
    }

    public VoiceGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
