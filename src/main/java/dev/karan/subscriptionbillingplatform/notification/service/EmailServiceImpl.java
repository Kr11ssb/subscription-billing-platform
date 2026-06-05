package dev.karan.subscriptionbillingplatform.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    @Override
    public void sendRenewalPaymentLink(String email, String customerName, String paymentUrl, LocalDate expirydate) {

    }
}
