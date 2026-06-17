package dev.karan.subscriptionbillingplatform.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "processed_events" ,
        uniqueConstraints =  {@UniqueConstraint(
                        columnNames = {
                                "event_id",
                                "consumer_name"
                        }
                )
        }
)
public class ProcessedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private String consumerName;

    @Column(nullable = false)
    private LocalDateTime processedAt;
}
