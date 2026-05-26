package dev.karan.subscriptionbillingplatform.plan.repository;

import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import dev.karan.subscriptionbillingplatform.plan.entity.PlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PlanRepository extends JpaRepository<Plan, Long> {

    boolean existsByName(String name);

    Optional<Plan> findByName(String name);

    List<Plan> findByStatus(PlanStatus status);

    Optional<Plan> findByIdAndStatus(Long id, PlanStatus status);

}
