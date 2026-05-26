package dev.karan.subscriptionbillingplatform.subscription.repository;

import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.Flow;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

          boolean existsByPlanIdAndStatus(Long planId, SubscriptionStatus status);

    SubscriptionStatus status(SubscriptionStatus status);
}
