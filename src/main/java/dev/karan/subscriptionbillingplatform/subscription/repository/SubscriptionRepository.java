package dev.karan.subscriptionbillingplatform.subscription.repository;

import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsByPlanIdAndStatus(Long planId, SubscriptionStatus status);

    boolean existsByUserIdAndStatusIn(Long userId, Collection<SubscriptionStatus> statuses);

    Optional<Subscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);

    List<Subscription> findByStatusAndEndDateBefore(SubscriptionStatus status, LocalDate date);

    List<Subscription> findByStatusAndAutoRenewAndEndDateLessThanEqual(
     SubscriptionStatus status
    ,Boolean autoRenew
    ,LocalDate endDate);

}
