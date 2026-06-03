package dev.karan.subscriptionbillingplatform.payment.adapter;

import dev.karan.subscriptionbillingplatform.payment.gateway.PaymentGatewayRequest;
import dev.karan.subscriptionbillingplatform.payment.gateway.PaymentGatewayResponse;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentGateway;

public interface PaymentGatewayAdapter {

    PaymentGatewayResponse createPayment(PaymentGatewayRequest request);

    PaymentGateway getSupportedGateway();
}
