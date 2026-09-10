package com.voiceforge.authservice.service;

import com.voiceforge.authservice.dto.request.LoginRequest;
import com.voiceforge.authservice.dto.request.RegisterRequest;
import com.voiceforge.authservice.dto.response.LoginResponse;
import com.voiceforge.authservice.dto.response.RegisterResponse;
import com.voiceforge.authservice.dto.response.UserResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UserResponse getCurrentUser();
}
