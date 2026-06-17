package dev.karan.subscriptionbillingplatform.common.repository;

import dev.karan.subscriptionbillingplatform.common.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository
        extends JpaRepository<ProcessedEvent, Long> {

    boolean existsByEventIdAndConsumerName(
            String eventId,
            String consumerName);
}
