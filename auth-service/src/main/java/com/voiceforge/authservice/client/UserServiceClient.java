package com.voiceforge.authservice.client;
import com.voiceforge.authservice.dto.request.CreateUserRequest;

public interface UserServiceClient {
    void createUser(CreateUserRequest request);
}
