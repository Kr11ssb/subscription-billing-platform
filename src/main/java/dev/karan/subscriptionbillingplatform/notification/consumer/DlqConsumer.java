package dev.karan.subscriptionbillingplatform.notification.consumer;

import dev.karan.subscriptionbillingplatform.notification.event.RenewalEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DlqConsumer {

    @KafkaListener(
            topics = "renewal-email-topic-dlq",
            groupId = "dlq-group"
    )

    public void consumeDlq(RenewalEmailEvent event){

        log.error("Message moved to DLQ for customer {}", event.customerName());
    }
}
