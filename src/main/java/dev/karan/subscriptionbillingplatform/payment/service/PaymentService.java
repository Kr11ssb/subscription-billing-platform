package dev.karan.subscriptionbillingplatform.payment.service;

import dev.karan.subscriptionbillingplatform.payment.dto.CreatePaymentRequest;
import dev.karan.subscriptionbillingplatform.payment.dto.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentByReference(String paymentReference);

    Page<PaymentResponse> getPaymentBySubscription(
            Long subscriptionId, Pageable pageable);
}
