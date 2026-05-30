package dev.karan.subscriptionbillingplatform.payment.dto;

import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;

public class CreatePaymentresponse {

    private String PaymentReference;

    private PaymentStatus status;

    private String clientSecret;
}
