package com.voiceforge.authservice.service.impl;

import com.voiceforge.authservice.client.UserServiceClient;
import com.voiceforge.authservice.dto.request.CreateUserRequest;
import com.voiceforge.authservice.dto.request.LoginRequest;
import com.voiceforge.authservice.dto.request.RegisterRequest;
import com.voiceforge.authservice.dto.response.LoginResponse;
import com.voiceforge.authservice.dto.response.RegisterResponse;
import com.voiceforge.authservice.dto.response.UserResponse;
import com.voiceforge.authservice.entity.AuthProvider;
import com.voiceforge.authservice.entity.Role;
import com.voiceforge.authservice.entity.User;
import com.voiceforge.authservice.exception.InvalidCredentialsException;
import com.voiceforge.authservice.exception.ResourceAlreadyExistsException;
import com.voiceforge.authservice.exception.ResourceNotFoundException;
import com.voiceforge.authservice.repository.UserRepository;
import com.voiceforge.authservice.security.JwtService;
import com.voiceforge.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authProvider(AuthProvider.LOCAL)
                .role(Role.USER)
                .build();

        userRepository.save(user);

        userServiceClient.createUser(
                CreateUserRequest.builder()
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .authProvider(AuthProvider.LOCAL)
                        .build()
        );

        return RegisterResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("User registered successfully")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getAuthProvider() != AuthProvider.LOCAL) {
            throw new InvalidCredentialsException(
                    "This account uses Google login. Please continue with Google."
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }

    @Override
    public UserResponse getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
