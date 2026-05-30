package dev.karan.subscriptionbillingplatform.subscription.dto;

import dev.karan.subscriptionbillingplatform.auth.entity.User;
import dev.karan.subscriptionbillingplatform.subscription.entity.BillingCycle;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionRequestDTO {

    /*@NotNull
    private Long userId;*/

    @NotNull
    private Long planId;

    @NotNull
    private BillingCycle billingCycle;
}
