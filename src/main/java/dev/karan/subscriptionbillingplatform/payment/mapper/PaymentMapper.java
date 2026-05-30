package dev.karan.subscriptionbillingplatform.payment.mapper;

import dev.karan.subscriptionbillingplatform.payment.dto.PaymentResponse;
import dev.karan.subscriptionbillingplatform.payment.entity.Payment;

public interface PaymentMapper {

    PaymentResponse toResponseDTO(Payment payment);
}
