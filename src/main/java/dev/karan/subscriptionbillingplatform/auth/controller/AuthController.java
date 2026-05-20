package dev.karan.subscriptionbillingplatform.auth.controller;

import dev.karan.subscriptionbillingplatform.auth.dto.AuthResponseDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.LoginRequestDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.LoginResponseDTO;
import dev.karan.subscriptionbillingplatform.auth.dto.RegisterRequestDTO;
import dev.karan.subscriptionbillingplatform.auth.service.AuthService;
import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO requestDTO){
       AuthResponseDTO response = authService.register(requestDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"User registered successfully",response, LocalDateTime.now()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO response = authService.login(requestDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"User logged-in successfully",response,LocalDateTime.now()));
    }
}
