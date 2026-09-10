package com.voiceforge.authservice.security;

import com.voiceforge.authservice.client.UserServiceClient;
import com.voiceforge.authservice.dto.request.CreateUserRequest;
import com.voiceforge.authservice.entity.AuthProvider;
import com.voiceforge.authservice.entity.Role;
import com.voiceforge.authservice.entity.User;
import com.voiceforge.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final UserRepository userRepository;
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String authenticateGoogleUser(OAuth2User oauthUser) {

        String email = oauthUser.getAttribute("email");
        String fullName = oauthUser.getAttribute("name");

        if (email == null || email.isBlank()) {
            throw new IllegalStateException(
                    "Google account does not provide an email"
            );
        }

        User user = userRepository
                .findByEmail(email)
                .orElseGet(() ->
                        createGoogleUser(email, fullName)
                );

        return jwtService.generateToken(user.getEmail());
    }

    private User createGoogleUser(String email, String fullName) {

        String randomPassword = UUID.randomUUID().toString();

        User user = User.builder()
                .fullName(
                        fullName != null && !fullName.isBlank()
                                 ? fullName
                                 : email
                )
                .email(email)
                .password(
                        passwordEncoder.encode(randomPassword)
                )
                .authProvider(AuthProvider.GOOGLE)
                .role(Role.USER)
                .build();

        user = userRepository.save(user);

        userServiceClient.createUser(
                CreateUserRequest.builder()
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .authProvider(AuthProvider.GOOGLE)
                        .build()
        );

        return user;
    }
}
