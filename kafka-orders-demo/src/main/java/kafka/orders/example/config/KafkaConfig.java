package kafka.orders.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaConfig {
    private String bootstrapServers;
    private String consumerGroup;
    private String inventoryGroup;
    private String aggregatedGroup;
    private String dlqGroup;
    private String notificationsGroup;
    private String paymentsGroup;
    private String ordersStreams;
    private String ordersTopic;
    private String paymentsTopic;
    private String inventoryTopic;
    private String notificationsTopic;
    private String aggregatedTopic;
    private String dlqTopic;
}
