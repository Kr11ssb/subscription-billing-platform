package dev.karan.subscriptionbillingplatform.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BusinessMetrics {

    private final Counter subscriptionCreatedCounter;
    private final Counter paymentSuccessCounter;
    private final Counter subscriptionRenewalCounter;

    public BusinessMetrics(MeterRegistry meterRegistry){
        System.out.println("=== BusinessMetrics Initialized ===");
        this.subscriptionCreatedCounter =
                Counter.builder("subscription")
                        .description("Total subscriptions created")
                        .register(meterRegistry);

        this.paymentSuccessCounter =
                Counter.builder("payment_success")
                        .description("Total successful payments")
                        .register(meterRegistry);

        this.subscriptionRenewalCounter =
                Counter.builder("subscription_renewal")
                        .description("Total subscription renewals")
                        .register(meterRegistry);
    }
}
