package dev.karan.subscriptionbillingplatform.payment.webhook;

import com.stripe.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;

    @PostMapping
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {

        System.out.println("WEBHOOK RECEIVED");

        stripeWebhookService.processWebhook(
                payload,
                signature);

        return ResponseEntity.ok().build();
    }

    private void handleCheckoutSessionCompleted( Event event){
        System.out.println("Inside checkout handler");
    }

}
