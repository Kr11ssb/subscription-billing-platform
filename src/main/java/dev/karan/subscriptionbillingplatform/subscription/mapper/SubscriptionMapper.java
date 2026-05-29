package dev.karan.subscriptionbillingplatform.subscription.mapper;

import dev.karan.subscriptionbillingplatform.subscription.dto.CreateSubscriptionRequestDTO;
import dev.karan.subscriptionbillingplatform.subscription.dto.SubscriptionResponseDTO;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;

public interface SubscriptionMapper {

    Subscription toEntity(CreateSubscriptionRequestDTO request);

    SubscriptionResponseDTO toResponseDTO(Subscription subscription);



}
