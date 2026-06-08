package dev.karan.subscriptionbillingplatform.notification.service;

import dev.karan.subscriptionbillingplatform.payment.entity.Payment;

import java.time.LocalDate;

public interface EmailService {

    void sendEmail(String to,
                   String subject,
                   String body);

    void sendRenewalPaymentLink(
            String email,
            String customerName,
            String paymentUrl,
            LocalDate expirydate);


}
