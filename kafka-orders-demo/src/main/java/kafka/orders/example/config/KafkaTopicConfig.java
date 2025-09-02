package kafka.orders.example.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaConfig.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic ordersTopic() {
        return new NewTopic(kafkaConfig.getOrdersTopic(), 3, (short) 1);
    }

    @Bean
    public NewTopic paymentsTopic() {
        return new NewTopic(kafkaConfig.getPaymentsTopic(), 3, (short) 1);
    }

    @Bean
    public NewTopic inventoryTopic() {
        return new NewTopic(kafkaConfig.getInventoryTopic(), 3, (short) 1);
    }

    @Bean
    public NewTopic notificationsTopic() {
        return new NewTopic(kafkaConfig.getNotificationsTopic(), 3, (short) 1);
    }

    @Bean
    public NewTopic aggregatedTopic() {
        return new NewTopic(kafkaConfig.getAggregatedTopic(), 3, (short) 1);
    }

    @Bean
    public NewTopic dlqTopic() {
        return new NewTopic(kafkaConfig.getDlqTopic(), 1, (short) 1);
    }
}
