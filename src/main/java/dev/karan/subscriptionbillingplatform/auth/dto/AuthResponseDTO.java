package dev.karan.subscriptionbillingplatform.auth.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {

    private String accessToken;

    private String message;

    private Long expiresIn;
}
