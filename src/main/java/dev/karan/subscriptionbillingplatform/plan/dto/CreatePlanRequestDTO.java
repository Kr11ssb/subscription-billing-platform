package dev.karan.subscriptionbillingplatform.plan.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreatePlanRequestDTO {

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal monthlyPrice;

    @PositiveOrZero
    private BigDecimal yearlyPrice;

    private List<String> features;

    @Min(0)
    private Integer trialDays;

    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;

}
