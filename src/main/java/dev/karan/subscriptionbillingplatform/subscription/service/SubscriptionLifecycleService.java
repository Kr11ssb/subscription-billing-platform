package dev.karan.subscriptionbillingplatform.subscription.service;

import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;

public interface SubscriptionLifecycleService {

    int expireSubscriptions();

    void activateSubscription(Subscription subscription);

    void renewSubscription(Subscription subscription);
}
