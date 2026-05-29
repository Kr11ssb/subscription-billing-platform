package dev.karan.subscriptionbillingplatform.subscription.repository;

import dev.karan.subscriptionbillingplatform.subscription.dto.SubscriptionResponseDTO;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.Flow;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsByPlanIdAndStatus(Long planId, SubscriptionStatus status);

    boolean existsByUserIdAndStatus(Long userId, SubscriptionStatus status);

    Optional<Subscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);

}
