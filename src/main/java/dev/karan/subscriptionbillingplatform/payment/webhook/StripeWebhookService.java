package dev.karan.subscriptionbillingplatform.payment.webhook;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import dev.karan.subscriptionbillingplatform.config.StripeProperties;
import dev.karan.subscriptionbillingplatform.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookService {

    private final StripeProperties stripeProperties;
    private final PaymentService paymentService;

    public void processWebhook(
            String payload,
            String signature) {

        Event event;

        try {
            event = Webhook.constructEvent(
                    payload,
                    signature,
                    stripeProperties.getWebhookSecret());

        } catch (SignatureVerificationException ex) {

            log.error("Invalid Stripe signature", ex);
            throw new RuntimeException("Invalid Stripe signature");
        }

        log.info("Received Stripe event: {}", event.getType());

        switch (event.getType()){

            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event);
                break;

            default:
                log.info("Unhandled Stripe event: {}",
                        event.getType());
        }
          }

          private void handleCheckoutSessionCompleted(Event event){

              log.info("ENTERED checkout.session.completed handler");

              EventDataObjectDeserializer deserializer =
                      event.getDataObjectDeserializer();

              Optional<StripeObject> stripeObject =
                      deserializer.getObject();

              if (stripeObject.isEmpty()){
                  log.warn("Stripe object not found");
                  return;
              }

              Session session =
                      (Session) stripeObject.get();

              String paymentReference =
                      session.getMetadata()
                              .get("paymentReference");

              String transactionId =
                      session.getPaymentIntent();

              log.info("Payment Reference: {}" , paymentReference);
              log.info("Transaction Id:{}" , transactionId);

              if (paymentReference == null){
                  log.warn("Payment reference not found in metadata");
                  return;
              }

              paymentService.markPaymentSuccessful(
                      paymentReference,
                      transactionId);
          }
}
