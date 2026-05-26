package dev.karan.subscriptionbillingplatform.plan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UpdatePlanRequestDTO {

    @PositiveOrZero
    private BigDecimal monthlyPrice;

    @PositiveOrZero
    private BigDecimal yearlyPrice;

    private List<String> features;

    @Min(0)
    private Integer trialDays;

}
