package kafka.orders.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaConfig {
    private String topic;
    private String dlqTopic = topic + ".dlq";
}
