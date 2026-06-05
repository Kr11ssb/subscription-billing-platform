package dev.karan.subscriptionbillingplatform.subscription.scheduler;

import dev.karan.subscriptionbillingplatform.subscription.service.RenewalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RenewalScheduler {

    private final RenewalService renewalService;

    @Scheduled(cron = "${subscription.renewal.cron}")
    public void renewSubscription(){

        log.info("Renewal scheduler started");

        int renewCount = renewalService.renewSubscription();

        if (renewCount == 0){
            log.info("No subscriptions found for renewal");
            return;
        }

        log.info("Renewed {} subscriptions", renewCount);
    }
}
