package dev.karan.subscriptionbillingplatform.payment.controller;

import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import dev.karan.subscriptionbillingplatform.payment.dto.CreatePaymentRequest;
import dev.karan.subscriptionbillingplatform.payment.dto.PaymentResponse;
import dev.karan.subscriptionbillingplatform.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@RequestMapping("/payments")
@RestController
@Tag(name = "Payments Management", description = "Payment APIs")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody CreatePaymentRequest request){

        PaymentResponse response = paymentService.createPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(
                "Payment is processed successfully", response));
    }

    @GetMapping("/payments/{paymentReference}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentByPaymentReference(
            @PathVariable String paymentReference){

        PaymentResponse response =
                paymentService.getPaymentByReference(paymentReference);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "payment reference fetched successfully", response));
    }

    @GetMapping("/payments/subscription/{subscriptionId}")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getPaymentBySubscription(
            @PathVariable Long subscriptionId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "initiatedAt") Pageable pageable) {

        Page<PaymentResponse> response = paymentService.getPaymentBySubscription(subscriptionId, pageable);

        return ResponseEntity.ok(ApiResponse.success(
                "All payment reference fetched by subscription", response));
    }

}
