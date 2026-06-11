package dev.karan.subscriptionbillingplatform.notification.event;

import java.time.LocalDate;
import java.util.UUID;

public record RenewalEmailEvent (
    UUID eventId,
    String email,
    String customerName,
    String paymentUrl,
    LocalDate expiryDate) {

}
