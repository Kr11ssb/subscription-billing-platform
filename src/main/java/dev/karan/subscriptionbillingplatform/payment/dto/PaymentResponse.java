package dev.karan.subscriptionbillingplatform.payment.dto;

import dev.karan.subscriptionbillingplatform.payment.enums.PaymentGateway;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentMethod;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private String paymentReference;

    private BigDecimal amount;

    private String currency;

    private PaymentStatus status;

    private PaymentGateway gateway;

    private PaymentMethod paymentMethod;

    private String failureReason;

    private LocalDateTime initiatedAt;

    private LocalDateTime completedAt;


}
