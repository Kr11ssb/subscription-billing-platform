package dev.karan.subscriptionbillingplatform.notification.consumer;

import dev.karan.subscriptionbillingplatform.notification.KafkaTopics;
import dev.karan.subscriptionbillingplatform.notification.event.RenewalEmailEvent;
import dev.karan.subscriptionbillingplatform.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(
                    delay = 5000,
                    multiplier = 2.0
            ), dltTopicSuffix = "-dlq"
    )

    @KafkaListener(
            topics = KafkaTopics.RENEWAL_EMAIL_TOPIC,
            groupId = "notification-group"
    )

    public void consumeRenewalEmail(RenewalEmailEvent event){

        log.info("Received renewal email event for {}", event.email());

        emailService.sendRenewalPaymentLink(
                event.email(),
                event.customerName(),
                event.paymentUrl(),
                event.expiryDate()
        );
    }
}
