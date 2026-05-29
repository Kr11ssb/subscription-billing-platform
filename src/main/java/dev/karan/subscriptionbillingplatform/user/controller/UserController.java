package dev.karan.subscriptionbillingplatform.user.controller;

import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import dev.karan.subscriptionbillingplatform.user.dto.UserProfileResponseDTO;
import dev.karan.subscriptionbillingplatform.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SecurityRequirement(name = "bearerAuth")
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponseDTO>> getMyProfile(){
        UserProfileResponseDTO response = userService.getCurrentUserProfile();
        return ResponseEntity.status(HttpStatus.OK).
                body(new ApiResponse<>(true,
                        "My Profile",
                        response,
                        LocalDateTime.now()));
    }

}
