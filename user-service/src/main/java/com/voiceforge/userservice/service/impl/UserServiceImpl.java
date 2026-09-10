package com.voiceforge.userservice.service.impl;

import com.voiceforge.userservice.dto.request.CreateUserRequest;
import com.voiceforge.userservice.dto.request.UpdateCreditsRequest;
import com.voiceforge.userservice.dto.request.UpdateProfileRequest;
import com.voiceforge.userservice.dto.response.CreditsResponse;
import com.voiceforge.userservice.dto.response.UserResponse;
import com.voiceforge.userservice.entity.SubscriptionPlan;
import com.voiceforge.userservice.entity.User;
import com.voiceforge.userservice.exception.ResourceAlreadyExistsException;
import com.voiceforge.userservice.exception.ResourceNotFoundException;
import com.voiceforge.userservice.repository.UserRepository;
import com.voiceforge.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
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
                .avatarUrl(user.getAvatarUrl())
                .credits(user.getCredits())
                .subscriptionPlan(user.getSubscriptionPlan())
                .build();
    }

    @Override
    public UserResponse updateProfile(UpdateProfileRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .credits(user.getCredits())
                .subscriptionPlan(user.getSubscriptionPlan())
                .build();
    }

    @Override
    public CreditsResponse getCredits() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return CreditsResponse.builder()
                .credits(user.getCredits())
                .build();
    }

    @Override
    public CreditsResponse updateCredits(UpdateCreditsRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        int updatedCredits = user.getCredits() + request.getCredits();

        if (updatedCredits < 0) {
            throw new IllegalArgumentException("Insufficient credits");
        }

        user.setCredits(updatedCredits);

        userRepository.save(user);

        return CreditsResponse.builder()
                .credits(user.getCredits())
                .build();
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .credits(100)
                .subscriptionPlan(SubscriptionPlan.FREE)
                .authProvider(request.getAuthProvider())
                .build();


        user = userRepository.save(user);

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .credits(user.getCredits())
                .subscriptionPlan(user.getSubscriptionPlan())
                .build();
    }
}
