package dev.karan.subscriptionbillingplatform.payment.dto;

import dev.karan.subscriptionbillingplatform.payment.enums.PaymentGateway;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull
    private Long subscriptionId;

    @NotNull
    private PaymentGateway gateway;
}
