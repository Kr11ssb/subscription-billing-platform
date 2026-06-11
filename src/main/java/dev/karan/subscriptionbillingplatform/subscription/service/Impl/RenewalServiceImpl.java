package dev.karan.subscriptionbillingplatform.subscription.service.Impl;

import dev.karan.subscriptionbillingplatform.notification.event.RenewalEmailEvent;
import dev.karan.subscriptionbillingplatform.notification.producer.NotificationProducer;
import dev.karan.subscriptionbillingplatform.notification.service.EmailService;
import dev.karan.subscriptionbillingplatform.payment.entity.Payment;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentPurpose;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import dev.karan.subscriptionbillingplatform.payment.repository.PaymentRepository;
import dev.karan.subscriptionbillingplatform.payment.service.PaymentService;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import dev.karan.subscriptionbillingplatform.subscription.repository.SubscriptionRepository;
import dev.karan.subscriptionbillingplatform.subscription.service.RenewalService;
import lombok.RequiredArgsConstructor;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RenewalServiceImpl implements RenewalService {

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final NotificationProducer notificationProducer;

    @Override
    @Transactional
    public int renewSubscription() {

        List<Subscription> subscriptionToRenew =
                subscriptionRepository.findByStatusAndAutoRenewAndEndDateLessThanEqual(
                        SubscriptionStatus.ACTIVE,
                        true
                        , LocalDate.now().plusDays(7));

        if (subscriptionToRenew.isEmpty()) {
            return 0;
        }

        int renewalCount = 0;

        for (Subscription subscription : subscriptionToRenew) {

            //Idempotency Check
            boolean renewalPending =
                    paymentRepository.existsBySubscriptionAndPurposeAndStatus(
                    subscription,
                    PaymentPurpose.RENEWAL,
                    PaymentStatus.PENDING);

            if (renewalPending) {
                log.info(
                        "Renewal payment already exists for subscription {}", subscription.getId());
                continue;
            }

            Payment payment =
                    paymentService.createRenewalPayment(subscription);

            if (payment == null) {
                continue;
        }

            RenewalEmailEvent event =
                    buildRenewalEvent(subscription, payment);

            notificationProducer.sendRenewalEmail(event);

            log.info(
                    "Published renewal event {} for subscription {}",
                    event.eventId(),
                    subscription.getId()
            );

        renewalCount++;
        log.info("Renewal email event published for subscription {}",
                subscription.getId());
    }

        return renewalCount;
    }

    public RenewalEmailEvent buildRenewalEvent(
            Subscription subscription, Payment payment){

        return new RenewalEmailEvent(
                UUID.randomUUID(),
                subscription.getUser().getEmail(),
                subscription.getUser().getName(),
                payment.getPaymentUrl(),
                subscription.getEndDate()
        );
    }
}