package dev.karan.subscriptionbillingplatform.subscription.service.Impl;

import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import dev.karan.subscriptionbillingplatform.subscription.repository.SubscriptionRepository;
import dev.karan.subscriptionbillingplatform.subscription.service.SubscriptionLifecycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionLifecycleServiceImpl
        implements SubscriptionLifecycleService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    @Override
    public int expireSubscriptions() {

        log.info("Checking for expired subscriptions");

        List<Subscription> expiredSubscriptions =
                subscriptionRepository.findByStatusAndEndDateBefore(
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now());

        if (expiredSubscriptions.isEmpty()) {
            return 0;
        }

        log.info("Found {} expired subscriptions", expiredSubscriptions.size());

        for (Subscription subscription : expiredSubscriptions) {

            log.info("Marking subscription {} as EXPIRED", subscription.getId());

            subscription.setStatus(SubscriptionStatus.EXPIRED);
        }

        subscriptionRepository.saveAll(expiredSubscriptions);

        return expiredSubscriptions.size();
    }

    @Transactional
    @Override
    public void activateSubscription(Subscription subscription) {

        LocalDate startDate = LocalDate.now();

        LocalDate endDate = switch (subscription.getBillingCycle()) {
            case MONTHLY -> startDate.plusMonths(1);

            case YEARLY -> startDate.plusYears(1);
        };

        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setStatus(SubscriptionStatus.ACTIVE);

    }

    @Transactional
    @Override
    public void renewSubscription(Subscription subscription) {

        LocalDate baseDate;

        if (subscription.getEndDate().isAfter(LocalDate.now())) {
            baseDate = subscription.getEndDate();
        } else {
            baseDate = LocalDate.now();
        }

        LocalDate newEndDate = switch
                (subscription.getBillingCycle()){

            case MONTHLY -> baseDate.plusMonths(1);

            case YEARLY -> baseDate.plusYears(1);
        };

        subscription.setEndDate(newEndDate);
        subscription.setStatus(SubscriptionStatus.ACTIVE);

    }
}
