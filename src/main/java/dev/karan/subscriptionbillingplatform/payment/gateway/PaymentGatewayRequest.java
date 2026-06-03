package dev.karan.subscriptionbillingplatform.payment.gateway;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentGatewayRequest {


    private String paymentReference;

    private BigDecimal amount;

    private String currency;

    private String customerEmail;

    private String customerName;

}
