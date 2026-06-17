package dev.karan.subscriptionbillingplatform.plan.service;

import dev.karan.subscriptionbillingplatform.common.exception.BusinessValidationException;
import dev.karan.subscriptionbillingplatform.common.exception.DuplicateResourceException;
import dev.karan.subscriptionbillingplatform.common.exception.ResourceNotFoundException;
import dev.karan.subscriptionbillingplatform.plan.dto.CreatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.PlanResponseDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.UpdatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import dev.karan.subscriptionbillingplatform.plan.entity.PlanStatus;
import dev.karan.subscriptionbillingplatform.plan.mapper.PlanMapper;
import dev.karan.subscriptionbillingplatform.plan.repository.PlanRepository;
import dev.karan.subscriptionbillingplatform.subscription.entity.SubscriptionStatus;
import dev.karan.subscriptionbillingplatform.subscription.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    @CacheEvict(value = "plans", allEntries = true)

    public PlanResponseDTO createPlan(CreatePlanRequestDTO request) {

        if(planRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Plan with name '" + request.getName() + "' already exists");
        }
        //Convert request DTO into entity using mapper
        Plan plan = planMapper.toEntity(request);
        plan.setStatus(PlanStatus.ACTIVE);
        Plan savedPlan = planRepository.save(plan);
        return planMapper.toResponseDTO(savedPlan);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "plan", key = "#planId"),
                      @CacheEvict(value = "plans", allEntries = true)})
    public PlanResponseDTO updatePlan(Long planId, UpdatePlanRequestDTO request) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan with id " + planId + " not found"));

        if(plan.getStatus() != PlanStatus.ACTIVE) {
            throw new BusinessValidationException("Only active plans can be updated");
        }

        planMapper.applyPatch(plan,request);
        Plan updatedPlan = planRepository.save(plan);
        return planMapper.toResponseDTO(plan);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "plan", key = "#planId"),
            @CacheEvict(value = "plans", allEntries = true)})

    public PlanResponseDTO deactivatePlan(Long planId) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan with id " + planId + " not found"));

        if(plan.getStatus() != PlanStatus.ACTIVE){
            throw new BusinessValidationException("Only active plans can be deactivated");
        }

        if(subscriptionRepository.existsByPlanIdAndStatus(planId, SubscriptionStatus.ACTIVE)){
            throw new BusinessValidationException("Plan cannot be deactivated because active subscriptions exist");
        }

        plan.setStatus(PlanStatus.INACTIVE);

        return planMapper.toResponseDTO(plan);
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "plan",
            key = "#planId")

    public PlanResponseDTO getPlanById(Long planId) {

        log.info("Fetching plan from database: {}", planId);

        Plan plan = planRepository.findByIdAndStatus(planId, PlanStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan with id " + planId + " not found"));

        return planMapper.toResponseDTO(plan);
    }

    @Override
    @Cacheable(value = "plans",
    key = "#pageable.pageNumber + '-' + #pageable.pageSize")

    public Page<PlanResponseDTO> getAllPlans(Pageable pageable) {
        Page<Plan> planPage = planRepository.findAll(pageable);
        return planPage.map(planMapper::toResponseDTO);
    }

}
