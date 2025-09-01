package kafka.orders.example.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import lombok.RequiredArgsConstructor;

/**
 * - В Kafka по умолчанию топики надо создавать вручную (CLI или Admin API). - Spring сделал обёртку
 * KafkaAdmin + NewTopic, чтобы можно было описывать топики декларативно прямо в коде.
 *
 * - При старте приложения Spring поднимает KafkaAdmin. - Находит все бины типа NewTopic. - Через
 * Kafka Admin API (CreateTopicsRequest) проверяет: есть ли топик. -> если нет: создаёт с заданным
 * числом партиций и реплик. -> если есть: ничего не делает.
 *
 * - Гарантия, что все нужные топики существуют ДО того, как начнут работать продюсеры и консьюмеры.
 * - Infrastructure as Code: топики описаны прямо в Java-коде и их не нужно создавать руками.
 */

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        // KafkaAdmin — бин, который подключается к кластеру Kafka по bootstrap.servers.
        // Spring будет использовать его для вызова Admin API.
        Map<String, Object> configs = new HashMap<>();
        configs.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                kafkaConfig.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic ordersTopic() {
        // NewTopic = декларация топика.
        // (имя, количество партиций, фактор репликации).
        // Здесь: 3 партиции, 1 копия.
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
        // Dead Letter Queue (DLQ) топик — для сообщений, которые не удалось обработать.
        // Cоздаётся с 1 партицией, потому что это "очередь ошибок".
        return new NewTopic(kafkaConfig.getDlqTopic(), 1, (short) 1);
    }
}
