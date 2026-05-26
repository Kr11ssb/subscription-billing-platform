package dev.karan.subscriptionbillingplatform.plan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal monthlyPrice;

    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal yearlyPrice;

    @ElementCollection
    @CollectionTable(name= "plan_features",
            joinColumns = @JoinColumn(name = "plan_id" ))
    @Column(name = "feature_name")
    private List<String> features;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false)
    @Min(0)
    private Integer trialDays;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    @Column(nullable = false)
    @Version
    private Long version;

}
