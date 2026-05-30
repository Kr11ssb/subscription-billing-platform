package dev.karan.subscriptionbillingplatform.subscription.mapper;

import dev.karan.subscriptionbillingplatform.subscription.dto.CreateSubscriptionRequestDTO;
import dev.karan.subscriptionbillingplatform.subscription.dto.SubscriptionResponseDTO;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapperImpl implements SubscriptionMapper{

    @Override
    public Subscription toEntity(CreateSubscriptionRequestDTO request) {
        Subscription subscription = new Subscription();
        subscription.setBillingCycle(request.getBillingCycle());  //billingCycle is DTO-owned input
        return subscription;
    }

    @Override
    public SubscriptionResponseDTO toResponseDTO(Subscription subscription) {

        return SubscriptionResponseDTO.builder()
                .subscriptionId(subscription.getId())
                .userId(subscription.getUser().getId())
                .planId(subscription.getPlan().getId())
                .planName(subscription.getPlan().getName())
                .billingCycle(subscription.getBillingCycle())
                .status(subscription.getStatus())
                .startDate(subscription.getStartDate())
                .enddate(subscription.getEndDate())
                .build();
    }

}
