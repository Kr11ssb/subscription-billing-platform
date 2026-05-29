package dev.karan.subscriptionbillingplatform.subscription.service;

import dev.karan.subscriptionbillingplatform.auth.entity.UserStatus;
import dev.karan.subscriptionbillingplatform.auth.repository.UserRepository;
import dev.karan.subscriptionbillingplatform.common.exception.BusinessValidationException;
import dev.karan.subscriptionbillingplatform.common.exception.DuplicateResourceException;
import dev.karan.subscriptionbillingplatform.common.exception.ResourceNotFoundException;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import dev.karan.subscriptionbillingplatform.plan.entity.PlanStatus;
import dev.karan.subscriptionbillingplatform.plan.repository.PlanRepository;
import dev.karan.subscriptionbillingplatform.subscription.dto.CreateSubscriptionRequestDTO;
import dev.karan.subscriptionbillingplatform.subscription.dto.SubscriptionResponseDTO;
import dev.karan.subscriptionbillingplatform.subscription.entity.BillingCycle;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import dev.karan.subscriptionbillingplatform.subscription.mapper.SubscriptionMapper;
import dev.karan.subscriptionbillingplatform.subscription.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import dev.karan.subscriptionbillingplatform.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus.ACTIVE;

@Service
@AllArgsConstructor
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService{

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;


    @Override
    public SubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + request.getUserId() + " not found"));

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessValidationException("Only active user can create subscription");
        }

        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plan with id " +request.getPlanId() + " not found"));

        if (plan.getStatus() != PlanStatus.ACTIVE) {
            throw new BusinessValidationException("Only active plan can be subscribed");
        }

        if (subscriptionRepository.existsByUserIdAndStatus(
                user.getId(), ACTIVE)) {
            throw new DuplicateResourceException("User already has an active subscription");
        }

        Subscription subscription = subscriptionMapper.toEntity(request);

        LocalDate today = LocalDate.now();

        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(ACTIVE);
        subscription.setStartDate(today);

        if (request.getBillingCycle() == BillingCycle.MONTHLY){
            subscription.setEndDate(today.plusMonths(1));
        } else  {
            subscription.setEndDate(today.plusYears(1));
        }

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponseDTO(savedSubscription);
    }

    @Transactional(readOnly = true)
    @Override
    public SubscriptionResponseDTO getSubscriptionById(Long subscriptionId) {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription with id " +subscriptionId + " not found"));

        return subscriptionMapper.toResponseDTO(subscription);
    }

    @Override
    public SubscriptionResponseDTO getActiveSubscriptionForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + userId + " not found"));

        Subscription subscription = subscriptionRepository.findByUserIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active subscription not found for user id " +userId));

        return subscriptionMapper.toResponseDTO(subscription);
    }

    @Transactional
    @Override
    public SubscriptionResponseDTO cancelSubscription(Long subscriptionid) {

        Subscription subscription = subscriptionRepository.findById(subscriptionid)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription with id " + subscriptionid + " not found"));

        if(subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new BusinessValidationException("Only active subscription can be cancelled");
        }

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setEndDate(LocalDate.now());

        Subscription updateSubscription = subscriptionRepository.save(subscription);

        return subscriptionMapper.toResponseDTO(updateSubscription);
    }


    @Override
    public Page<SubscriptionResponseDTO> getAllSubscriptions(Pageable pageable) {

        Page<Subscription> subscriptionPage = subscriptionRepository.findAll(pageable);
        return subscriptionPage.map(subscriptionMapper::toResponseDTO);
    }


}
