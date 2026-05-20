package dev.karan.subscriptionbillingplatform.auth.service;

import dev.karan.subscriptionbillingplatform.auth.dto.AuthResponseDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.LoginRequestDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.LoginResponseDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
