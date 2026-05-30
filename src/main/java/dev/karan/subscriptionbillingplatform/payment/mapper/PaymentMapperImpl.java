package dev.karan.subscriptionbillingplatform.payment.mapper;

import dev.karan.subscriptionbillingplatform.payment.dto.PaymentResponse;
import dev.karan.subscriptionbillingplatform.payment.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponseDTO(Payment payment) {

        return PaymentResponse.builder()
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .gateway(payment.getGateway())
                .paymentMethod(payment.getPaymentMethod())
                .failureReason(payment.getFailureReason())
                .initiatedAt(payment.getInitiatedAt())
                .status(payment.getStatus())
                .completedAt(payment.getCompletedAt())
                .build();
    }
}










