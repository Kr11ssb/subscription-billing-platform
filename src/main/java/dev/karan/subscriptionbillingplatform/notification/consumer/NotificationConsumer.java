package dev.karan.subscriptionbillingplatform.notification.consumer;

import dev.karan.subscriptionbillingplatform.common.entity.ProcessedEvent;
import dev.karan.subscriptionbillingplatform.common.repository.ProcessedEventRepository;
import dev.karan.subscriptionbillingplatform.notification.KafkaTopics;
import dev.karan.subscriptionbillingplatform.notification.event.RenewalEmailEvent;
import dev.karan.subscriptionbillingplatform.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private static final String EMAIL_CONSUMER = "EMAIL";

    private final EmailService emailService;
    private final ProcessedEventRepository processedEventRepository;

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

        //Idempotency Check
        boolean processed = processedEventRepository.existsByEventIdAndConsumerName(
                event.eventId().toString(),
                "EMAIL"
        );

        if (processed){
            log.info("Duplicate event detected. Skipping eventId={}", event.eventId());
            return;
        }

        log.info("Received renewal email event. eventId={}, email={}",
                event.eventId(),
                event.email());

        //Send Email
        emailService.sendRenewalPaymentLink(
                event.email(),
                event.customerName(),
                event.paymentUrl(),
                event.expiryDate()
        );

        //Save processedEvent
        ProcessedEvent processedEvent = ProcessedEvent.builder()
                .eventId(event.eventId().toString())
                .consumerName(EMAIL_CONSUMER)
                .processedAt(LocalDateTime.now())
                .build();

        processedEventRepository.save(processedEvent);

        log.info("Marked event {} as processed", event.eventId());
    }
}
