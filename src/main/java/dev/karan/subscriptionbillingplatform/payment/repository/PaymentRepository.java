package dev.karan.subscriptionbillingplatform.payment.repository;

import dev.karan.subscriptionbillingplatform.payment.entity.Payment;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentPurpose;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(String paymentReference);

    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

    boolean existsByPaymentReference(String paymentReference);

    Page<Payment> findBySubscriptionId(Long subscriptionId , Pageable pageable);

    boolean existsBySubscriptionIdAndStatusIn(Long subscriptionId, Collection<PaymentStatus> statuses);

    boolean existsBySubscriptionAndPurposeAndStatus(
            Subscription subscription,
            PaymentPurpose purpose,
            PaymentStatus status);

    //for the latest successful status payment
    Optional<Payment> findTopBySubscriptionAndStatusOrderByInitiatedAtDesc(
            Subscription subscription,
            PaymentStatus status);

}
