package dev.karan.subscriptionbillingplatform.plan.service;

import dev.karan.subscriptionbillingplatform.plan.dto.CreatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.PlanResponseDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.UpdatePlanRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface PlanService {

    PlanResponseDTO createPlan(CreatePlanRequestDTO request);

    PlanResponseDTO updatePlan(Long planId, UpdatePlanRequestDTO request);

    PlanResponseDTO deactivatePlan(Long planId);

    PlanResponseDTO getPlanById(Long planId);

    Page<PlanResponseDTO> getAllPlans(Pageable pageable);


}

