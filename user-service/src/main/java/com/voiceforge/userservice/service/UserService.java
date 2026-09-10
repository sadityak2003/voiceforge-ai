package com.voiceforge.userservice.service;

import com.voiceforge.userservice.dto.request.CreateUserRequest;
import com.voiceforge.userservice.dto.request.UpdateCreditsRequest;
import com.voiceforge.userservice.dto.request.UpdateProfileRequest;
import com.voiceforge.userservice.dto.response.CreditsResponse;
import com.voiceforge.userservice.dto.response.UserResponse;

public interface UserService {
    UserResponse getCurrentUser();
    UserResponse updateProfile(UpdateProfileRequest request);
    CreditsResponse getCredits();
    CreditsResponse updateCredits(UpdateCreditsRequest request);
    UserResponse createUser(CreateUserRequest request);
}
