package dev.karan.subscriptionbillingplatform.plan.dto;

import dev.karan.subscriptionbillingplatform.plan.entity.PlanStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PlanResponseDTO {

    private Long planId;

    private String name;

    private BigDecimal monthlyPrice;

    private BigDecimal yearlyPrice;

    private List<String> features;

    private Integer trialDays;

    private String currency;

    private PlanStatus status;
}
