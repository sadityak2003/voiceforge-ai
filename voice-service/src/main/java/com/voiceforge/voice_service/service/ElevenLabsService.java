package com.voiceforge.voice_service.service;

public interface ElevenLabsService {
    byte[] generateSpeech(String text, String voiceId);
}
