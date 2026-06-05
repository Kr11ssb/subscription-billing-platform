package dev.karan.subscriptionbillingplatform.notification.controller;

import dev.karan.subscriptionbillingplatform.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/test-email")
    public ResponseEntity<String> sendTestMail(){

        emailService.sendEmail("karanrabde@gmail.com",
                "Test-Email",
                "Email service is working");

    return ResponseEntity.ok("Email sent successfully");
}
}