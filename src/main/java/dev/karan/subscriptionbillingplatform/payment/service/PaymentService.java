package dev.karan.subscriptionbillingplatform.payment.service;

import com.stripe.model.SubscriptionItem;
import dev.karan.subscriptionbillingplatform.payment.dto.CreatePaymentRequest;
import dev.karan.subscriptionbillingplatform.payment.dto.PaymentResponse;
import dev.karan.subscriptionbillingplatform.payment.entity.Payment;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentByReference(String paymentReference);

    Page<PaymentResponse> getPaymentBySubscription(
            Long subscriptionId, Pageable pageable);

    void markPaymentSuccessful(
            String paymentReference, String transactionId);

    Payment createRenewalPayment(Subscription subscription);
}
