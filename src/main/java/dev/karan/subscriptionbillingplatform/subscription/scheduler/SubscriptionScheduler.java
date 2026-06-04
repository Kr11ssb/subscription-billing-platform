package dev.karan.subscriptionbillingplatform.subscription.scheduler;
import dev.karan.subscriptionbillingplatform.subscription.service.SubscriptionLifecycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionScheduler {

    private final SubscriptionLifecycleService subscriptionLifecycleService;

    @Scheduled(fixedRate = 60000)
    public void expireSubscriptions(){

       int expiredCount =
               subscriptionLifecycleService.expireSubscriptions();

        if (expiredCount == 0){
            log.info("No subscription found for expiration");
            return;
        }

        log.info("Expired {} subscriptions", expiredCount);


    }
}
