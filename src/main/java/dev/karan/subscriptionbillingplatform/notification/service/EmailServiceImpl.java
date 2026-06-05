package dev.karan.subscriptionbillingplatform.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to,
                          String subject,
                          String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@subscriptionbilling.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        log.info("Email sent successfully to {}", to);
    }

    @Override
    public void sendRenewalPaymentLink(String email,
                                       String customerName,
                                       String paymentUrl,
                                       LocalDate expirydate) {

        String subject = "Subscription Renewal Reminder";
        String body = """
        Hello %s,
        Your subscription expires on %s.
        Please renew using the link below:
        %s

        Thank you.
        """
                .formatted(customerName,expirydate,paymentUrl);

        sendEmail(email, subject, body);
    }
}
