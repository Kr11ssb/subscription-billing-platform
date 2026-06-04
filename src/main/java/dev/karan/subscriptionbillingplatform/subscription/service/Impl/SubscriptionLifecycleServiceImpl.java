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

        List<Subscription> expiredSubscriptions =
                subscriptionRepository.findByStatusAndEndDateBefore(
                        SubscriptionStatus.ACTIVE,
                        LocalDate.now());

        if (expiredSubscriptions.isEmpty()) {
            return 0;
        }

        for (Subscription subscription : expiredSubscriptions) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
        }

        subscriptionRepository.saveAll(expiredSubscriptions);

        return expiredSubscriptions.size();
    }
}
