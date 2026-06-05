package dev.karan.subscriptionbillingplatform.notification.service;

import java.time.LocalDate;

public interface EmailService {

    void sendRenewalPaymentLink(
            String email,
            String customerName,
            String paymentUrl,
            LocalDate expirydate);
}
