package dev.karan.subscriptionbillingplatform.notification.controller;

import dev.karan.subscriptionbillingplatform.subscription.service.RenewalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class RenewalTestController {

    private final RenewalService renewalService;

    @PostMapping("/renewals")
    public String triggerRenewals(){

        int count = renewalService.renewSubscription();

        return "Renewal email triggered for "+ count + "subscriptions";
    }
}
