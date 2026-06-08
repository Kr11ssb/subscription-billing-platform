package dev.karan.subscriptionbillingplatform.notification.producer;

import dev.karan.subscriptionbillingplatform.notification.KafkaTopics;
import dev.karan.subscriptionbillingplatform.notification.event.RenewalEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, RenewalEmailEvent> kafkaTemplate;

    public void sendRenewalEmail(RenewalEmailEvent event){

        log.info("Publishing renewal email event for {}",event.email());

        kafkaTemplate.send(KafkaTopics.RENEWAL_EMAIL_TOPIC,event);
    }
}
