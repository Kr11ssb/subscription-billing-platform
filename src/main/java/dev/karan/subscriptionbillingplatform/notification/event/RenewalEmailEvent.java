package dev.karan.subscriptionbillingplatform.notification.event;

import java.time.LocalDate;

public record RenewalEmailEvent (
    String email,
    String customerName,
    String paymentUrl,
    LocalDate expiryDate) {

}
