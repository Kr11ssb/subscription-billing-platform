package dev.karan.subscriptionbillingplatform.subscription.entity;

import dev.karan.subscriptionbillingplatform.auth.entity.User;
import dev.karan.subscriptionbillingplatform.common.entity.BaseEntity;
import dev.karan.subscriptionbillingplatform.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "subscription")
@NoArgsConstructor
public class Subscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id" , nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private BillingCycle billingCycle;

    @Column(nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

}
