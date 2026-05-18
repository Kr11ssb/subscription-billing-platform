package dev.karan.subscriptionbillingplatform.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {

    private String accessToken;

    private String message;

    private Long expiresIn;
}
