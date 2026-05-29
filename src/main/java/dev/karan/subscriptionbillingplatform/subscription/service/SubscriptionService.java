package dev.karan.subscriptionbillingplatform.subscription.service;
import dev.karan.subscriptionbillingplatform.subscription.dto.CreateSubscriptionRequestDTO;
import dev.karan.subscriptionbillingplatform.subscription.dto.SubscriptionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SubscriptionService {

    SubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO request);

    SubscriptionResponseDTO getSubscriptionById(Long subscriptionId);

    SubscriptionResponseDTO getActiveSubscriptionForUser(Long userId);

    SubscriptionResponseDTO cancelSubscription(Long subscriptionid);

    Page<SubscriptionResponseDTO> getAllSubscriptions(Pageable pageable);

}
