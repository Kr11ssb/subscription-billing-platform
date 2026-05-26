package dev.karan.subscriptionbillingplatform.plan.mapper;

import dev.karan.subscriptionbillingplatform.plan.dto.CreatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.PlanResponseDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.UpdatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;

public interface PlanMapper {

    Plan toEntity(CreatePlanRequestDTO request);

    PlanResponseDTO toResponseDTO(Plan plan);

    void applyPatch(Plan plan , UpdatePlanRequestDTO request);
}
