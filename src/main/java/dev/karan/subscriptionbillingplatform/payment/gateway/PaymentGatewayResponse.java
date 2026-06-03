package dev.karan.subscriptionbillingplatform.payment.gateway;

import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentGatewayResponse {

    private String gatewayOrderId;

    private String paymentUrl;

    private PaymentStatus status;

}
