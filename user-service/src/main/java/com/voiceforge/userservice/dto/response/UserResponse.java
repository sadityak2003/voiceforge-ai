package com.voiceforge.userservice.dto.response;

import com.voiceforge.userservice.entity.SubscriptionPlan;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID id;

    private String fullName;

    private String email;

    private String avatarUrl;

    private Integer credits;

    private SubscriptionPlan subscriptionPlan;
}
