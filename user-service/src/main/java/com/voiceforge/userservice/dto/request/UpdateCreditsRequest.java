package com.voiceforge.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCreditsRequest {

    @NotNull
    private Integer credits;

}
