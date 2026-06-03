package dev.karan.subscriptionbillingplatform.payment.service;

import dev.karan.subscriptionbillingplatform.auth.security.CustomUserDetailsService;
import dev.karan.subscriptionbillingplatform.common.exception.BusinessValidationException;
import dev.karan.subscriptionbillingplatform.common.exception.ResourceNotFoundException;
import dev.karan.subscriptionbillingplatform.payment.adapter.PaymentGatewayAdapter;
import dev.karan.subscriptionbillingplatform.payment.dto.CreatePaymentRequest;
import dev.karan.subscriptionbillingplatform.payment.dto.PaymentResponse;
import dev.karan.subscriptionbillingplatform.payment.entity.Payment;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import dev.karan.subscriptionbillingplatform.payment.factory.PaymentGatewayFactory;
import dev.karan.subscriptionbillingplatform.payment.gateway.PaymentGatewayRequest;
import dev.karan.subscriptionbillingplatform.payment.gateway.PaymentGatewayResponse;
import dev.karan.subscriptionbillingplatform.payment.mapper.PaymentMapper;
import dev.karan.subscriptionbillingplatform.payment.repository.PaymentRepository;
import dev.karan.subscriptionbillingplatform.payment.util.PaymentReferenceGenerator;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import dev.karan.subscriptionbillingplatform.subscription.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentReferenceGenerator paymentReferenceGenerator;


    private final PaymentGatewayFactory paymentGatewayFactory;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {

        Long subscriptionId = request.getSubscriptionId();

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription with id " + subscriptionId + " not found"));

        if (subscription.getStatus() != SubscriptionStatus.PENDING_PAYMENT) {
            throw new BusinessValidationException("Payment can only be created for subscriptions in PENDING_PAYMENT status");
        }

        if (paymentRepository.existsBySubscriptionIdAndStatusIn(subscriptionId,
                List.of(PaymentStatus.PENDING, PaymentStatus.PROCESSING))) {
            throw new BusinessValidationException("An active payment already exists for this subscription");
        }

        BigDecimal amount = calculateAmount(subscription);
        String paymentReference;
        //check generated reference already exists
        do {
            paymentReference = paymentReferenceGenerator.generate();
        } while (paymentRepository.existsByPaymentReference(paymentReference));

        Payment payment = Payment.builder()
                .paymentReference(paymentReference)
                .subscription(subscription)
                .amount(amount)
                .currency(subscription.getPlan().getCurrency())
                .gateway(request.getGateway())
                .status(PaymentStatus.PENDING)
                .initiatedAt(LocalDateTime.now())
                .build();

        //save payment
        Payment savedPayment = paymentRepository.save(payment);
        //get adapter
        PaymentGatewayAdapter adapter = paymentGatewayFactory.getAdapter(
                request.getGateway());

        PaymentGatewayRequest gatewayRequest = PaymentGatewayRequest.builder()
                .paymentReference(paymentReference)
                .amount(amount)
                .currency(payment.getCurrency())
                .customerEmail(subscription.getUser().getEmail())
                .customerName(subscription.getUser().getName())
                .build();

        PaymentGatewayResponse gatewayResponse =
                adapter.createPayment(gatewayRequest);

        savedPayment.setGatewayOrderId(
                gatewayResponse.getGatewayOrderId());

        savedPayment.setStatus(
                gatewayResponse.getStatus());

        savedPayment.setPaymentUrl(
                gatewayResponse.getPaymentUrl());

        savedPayment = paymentRepository.save(savedPayment);

        return paymentMapper.toResponseDTO(savedPayment);

    }

    private BigDecimal calculateAmount(Subscription subscription) {

        Plan plan = subscription.getPlan();

        return switch (subscription.getBillingCycle()) {
            case MONTHLY -> plan.getMonthlyPrice();
            case YEARLY -> plan.getYearlyPrice();
        };
    }

    @Override
    public PaymentResponse getPaymentByReference(String paymentReference) {

        Payment payment = paymentRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with reference " + paymentReference + " not found"));

        return paymentMapper.toResponseDTO(payment);
    }

    @Override
    public Page<PaymentResponse> getPaymentBySubscription(Long subscriptionId, Pageable pageable) {

        if (!subscriptionRepository.existsById(subscriptionId)) {
            throw new ResourceNotFoundException("Subscription with id " + subscriptionId + " not found");
        }

        Page<Payment> paymentPage =
                paymentRepository.findBySubscriptionId(subscriptionId, pageable);

        return paymentPage.map(paymentMapper::toResponseDTO);
    }

    @Transactional
    @Override
    public void markPaymentSuccessful(String paymentReference, String transactionId) {

        Payment payment = paymentRepository
                .findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));
        // Idempotency protection
        if (payment.getStatus() == PaymentStatus.SUCCESS)
            return;

        // Payment state transition
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setCompletedAt(LocalDateTime.now());
        payment.setGatewayPaymentId(transactionId);

        //Subscription activation
        Subscription subscription = payment.getSubscription();

        if (subscription.getStatus() == SubscriptionStatus.PENDING_PAYMENT) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

    }


}
