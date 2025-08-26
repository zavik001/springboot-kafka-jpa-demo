package kafka.orders.example.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import kafka.orders.example.config.KafkaConfig;
import kafka.orders.example.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaOrderConsumerService {

    private final KafkaConfig kafkaConfig;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrder(OrderResponseDto orderDto) {
        log.info("Read from the topic {}: {}", kafkaConfig.getTopic(), orderDto);
    }
}
