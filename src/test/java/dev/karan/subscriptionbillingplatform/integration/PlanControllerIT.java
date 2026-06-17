package dev.karan.subscriptionbillingplatform.integration;

import dev.karan.subscriptionbillingplatform.plan.dto.CreatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import dev.karan.subscriptionbillingplatform.plan.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PlanControllerIT extends BaseIntegrationTest{

    @Autowired
    private PlanRepository planRepository;

    @Test
    void shouldCreatePlanSuccessfully() throws Exception {

        CreatePlanRequestDTO request = new CreatePlanRequestDTO();
        request.setName("Premium");
        request.setCurrency("INR");
        request.setFeatures(List.of("dfg","kjh"));
        request.setTrialDays(4);
        request.setMonthlyPrice(BigDecimal.valueOf(77));
        request.setYearlyPrice(BigDecimal.valueOf(999));

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());

        Optional<Plan> savedPlan = planRepository.findByName("Premium");

        assertThat(savedPlan).isPresent();

        Plan plan = savedPlan.get();
        assertThat(plan.getName()).isEqualTo("Premium");
        assertThat(plan.getCurrency()).isEqualTo("INR");

    }


}
