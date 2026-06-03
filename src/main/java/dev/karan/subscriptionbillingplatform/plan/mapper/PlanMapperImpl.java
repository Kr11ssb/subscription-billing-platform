package dev.karan.subscriptionbillingplatform.plan.mapper;

import dev.karan.subscriptionbillingplatform.plan.dto.CreatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.PlanResponseDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.UpdatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapperImpl implements PlanMapper{

    @Override
    public Plan toEntity(CreatePlanRequestDTO request) {
        Plan plan = new Plan();
        plan.setName(request.getName());
        plan.setMonthlyPrice(request.getMonthlyPrice());
        plan.setYearlyPrice(request.getYearlyPrice());
        plan.setFeatures(request.getFeatures());
        plan.setCurrency(request.getCurrency());
        plan.setTrialDays(request.getTrialDays());

        return plan;
    }

    @Override
    public PlanResponseDTO toResponseDTO(Plan plan) {
        PlanResponseDTO response = new PlanResponseDTO();
        response.setPlanId(plan.getId());
        response.setName(plan.getName());
        response.setCurrency(plan.getCurrency());
        response.setMonthlyPrice(plan.getMonthlyPrice());
        response.setYearlyPrice(plan.getYearlyPrice());
        response.setTrialDays(plan.getTrialDays());
        response.setFeatures(plan.getFeatures());
        response.setStatus(plan.getStatus());

        return response;
    }

    @Override
    public void applyPatch(Plan plan, UpdatePlanRequestDTO request) {
    if(request.getMonthlyPrice()!=null) {
        plan.setMonthlyPrice(request.getMonthlyPrice());
    }
        if (request.getYearlyPrice()!=null) {
            plan.setYearlyPrice(request.getYearlyPrice());
        }
            if ((request.getFeatures()!=null)) {
                plan.setFeatures(request.getFeatures());
            }
                if (request.getTrialDays()!=null){
                    plan.setTrialDays(request.getTrialDays());
                }
    }
}
