package dev.karan.subscriptionbillingplatform.payment.entity;

import dev.karan.subscriptionbillingplatform.common.entity.BaseEntity;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentGateway;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentMethod;
import dev.karan.subscriptionbillingplatform.payment.enums.PaymentStatus;
import dev.karan.subscriptionbillingplatform.subscription.entity.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_reference", columnList = "payment_reference"),
        @Index(name = "idx_payment_subscription", columnList = "subscription_id"),
        @Index(name = "idx_payment_status", columnList = "status")
}
)
public class Payment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_reference",nullable = false, unique = true)
    private String paymentReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentGateway gateway;

    @Column(name = "payment_url", columnDefinition = "TEXT")
    private String paymentUrl;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "gateway_order_id" ,unique = true)
    private String gatewayOrderId;

    @Column(name = "gateway_payment_id" , unique = true)
    private String gatewayPaymentId;

    @Column(length = 500)
    private String failureReason;

    @Column(nullable = false)
    private LocalDateTime initiatedAt;

    private LocalDateTime completedAt;

}
