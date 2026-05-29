package dev.karan.subscriptionbillingplatform.auth.controller;

import dev.karan.subscriptionbillingplatform.auth.dto.*;
import dev.karan.subscriptionbillingplatform.auth.service.AuthService;
import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Management", description = "Auth APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register
            (@Valid @RequestBody RegisterRequestDTO requestDTO){

       AuthResponseDTO response = authService.register(requestDTO);

       return ResponseEntity.status(HttpStatus.CREATED)
               .body(ApiResponse.success(
                       "User registered successfully", response));
    }

    @PostMapping ("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login
            (@Valid @RequestBody LoginRequestDTO requestDTO){

        LoginResponseDTO response = authService.login(requestDTO);

        return  ResponseEntity.ok(
                ApiResponse.success("User logged-in successfully",
                        response));
    }

      @PostMapping("/refresh-token")
      public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken
              (@Valid @RequestBody RefreshRequestDTO requestDTO){

        LoginResponseDTO response = authService.refreshToken(requestDTO);

          return ResponseEntity.ok(
                  ApiResponse.success(
                          "Token refreshed successfully",
                          response));
      }

      @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout
              (@Valid @RequestBody RefreshRequestDTO requestDTO){

          authService.logout(requestDTO);

          return ResponseEntity.ok(
                  ApiResponse.success(
                          "Logged out successfully",
                          null
                  )
          );
      }
}


