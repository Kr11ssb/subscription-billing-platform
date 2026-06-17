package dev.karan.subscriptionbillingplatform;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.core.env.Environment;
import java.util.Arrays;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableCaching
public class SubscriptionBillingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionBillingPlatformApplication.class, args);
    }
}
