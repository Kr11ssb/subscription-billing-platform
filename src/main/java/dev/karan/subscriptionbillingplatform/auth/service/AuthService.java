package dev.karan.subscriptionbillingplatform.auth.service;

import dev.karan.subscriptionbillingplatform.auth.dto.*;
import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    LoginResponseDTO refreshToken(RefreshRequestDTO request);

    ApiResponse<Void> logout(RefreshRequestDTO request);

}