package dev.karan.subscriptionbillingplatform.payment.repository;

import dev.karan.subscriptionbillingplatform.payment.entity.Payment;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(String paymentReference);

    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

    boolean existsByPaymentReference(String paymentReference);

    Page<Payment> findBySubscriptionId(Long subscriptionId , Pageable pageable);

    boolean existsBySubscriptionIdAndStatusIn(Long subscriptionId, Collection<PaymentStatus> statuses);


}
