package dev.karan.subscriptionbillingplatform.subscription.dto;

import dev.karan.subscriptionbillingplatform.subscription.entity.BillingCycle;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponseDTO {

    private Long subscriptionId;

    private Long userId;

    private Long planId;

    private String planName;

    private BillingCycle billingCycle;

    private SubscriptionStatus status;

    private LocalDate startDate;

    private LocalDate enddate;


}
