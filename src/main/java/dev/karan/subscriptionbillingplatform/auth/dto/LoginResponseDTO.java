package dev.karan.subscriptionbillingplatform.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;
}
