package dev.karan.subscriptionbillingplatform.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentRedirectController {

    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("Payment completed successfully.");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        return ResponseEntity.ok("Payment was cancelled.");
}
}
