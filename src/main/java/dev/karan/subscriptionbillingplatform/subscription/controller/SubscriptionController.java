package dev.karan.subscriptionbillingplatform.subscription.controller;

import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import dev.karan.subscriptionbillingplatform.subscription.dto.CreateSubscriptionRequestDTO;
import dev.karan.subscriptionbillingplatform.subscription.dto.SubscriptionResponseDTO;
import dev.karan.subscriptionbillingplatform.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription Management", description = "Subscription APIs")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionResponseDTO>> createSubscription(
            @Valid @RequestBody CreateSubscriptionRequestDTO request){

        SubscriptionResponseDTO response = subscriptionService.createSubscription(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Subscription created successfully", response));
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponse<SubscriptionResponseDTO>> getSubscriptionById(
            @PathVariable Long subscriptionId){

     SubscriptionResponseDTO response = subscriptionService.getSubscriptionById(subscriptionId);

     return ResponseEntity.ok(ApiResponse.success("Subscription fetched successfully", response));
    }

    @GetMapping("/subscription/users/{userId}/active")
    public ResponseEntity<ApiResponse<SubscriptionResponseDTO>> getActiveSubscriptionForUser
            (@PathVariable Long userId){

        SubscriptionResponseDTO response = subscriptionService.getActiveSubscriptionForUser(userId);

        return ResponseEntity.ok(ApiResponse.success
                ("Active subscription fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SubscriptionResponseDTO>>> getAllSubscription(
            @ParameterObject
            @PageableDefault(size = 10) Pageable pageable){

        Page<SubscriptionResponseDTO> response = subscriptionService.getAllSubscriptions(pageable);

        return ResponseEntity.ok(ApiResponse.success(
                "All subscription fetched successfully", response));
    }

    @PostMapping("/{subscriptionId}/cancel")
    public ResponseEntity<ApiResponse<SubscriptionResponseDTO>> cancelSubscription(
            @PathVariable Long subscriptionId){

        SubscriptionResponseDTO response =
                subscriptionService.cancelSubscription(subscriptionId);

        return ResponseEntity.ok(
                ApiResponse.success(
                "Subscription cancelled successfully", response));
    }
}
