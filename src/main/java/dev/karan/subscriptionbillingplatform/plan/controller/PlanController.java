package dev.karan.subscriptionbillingplatform.plan.controller;

import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import dev.karan.subscriptionbillingplatform.plan.dto.CreatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.PlanResponseDTO;
import dev.karan.subscriptionbillingplatform.plan.dto.UpdatePlanRequestDTO;
import dev.karan.subscriptionbillingplatform.plan.service.PlanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/plans")
@Tag(name = "Plan Management", description = "Plan APIs")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlanResponseDTO>> createPlan(
            @Valid @RequestBody CreatePlanRequestDTO request){

        PlanResponseDTO response = planService.createPlan(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Plan created successfully",response));
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<ApiResponse<PlanResponseDTO>> updatePlan(
            @PathVariable Long planId,
            @Valid @RequestBody UpdatePlanRequestDTO request){
        PlanResponseDTO response = planService.updatePlan(planId, request);

        return ResponseEntity.ok(
                ApiResponse.success("Plan updated successfully", response));
    }

    @PatchMapping("/{planId}/deactivate")
    ResponseEntity<ApiResponse<PlanResponseDTO>> deactivatePlan(
    @PathVariable Long planId){
        PlanResponseDTO response = planService.deactivatePlan(planId);

        return ResponseEntity.ok(ApiResponse.success("Plan deactivated successfully", response));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<PlanResponseDTO>> getPlanById(
    @PathVariable Long planId){

        PlanResponseDTO response = planService.getPlanById(planId);

        return ResponseEntity.ok(ApiResponse.success("Plan fetched successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PlanResponseDTO>>> getAllPlans(
            @ParameterObject
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {

        Page<PlanResponseDTO> response = planService.getAllPlans(pageable);

        return ResponseEntity.ok(ApiResponse.success(
                "Plans fetched successfully", response));
    }
    }

