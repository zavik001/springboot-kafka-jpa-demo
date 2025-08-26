package kafka.orders.example.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import kafka.orders.example.config.KafkaConfig;
import kafka.orders.example.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaOrderProducerService {

    private final KafkaTemplate<String, OrderResponseDto> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    public void sendOrder(OrderResponseDto orderDto) {
        try {
            kafkaTemplate.send(kafkaConfig.getTopic(), orderDto.id().toString(), orderDto);
            log.info("Send to topic {}: {}", kafkaConfig.getTopic(), orderDto);
        } catch (Exception e) {
            log.error("Sending error: {}", e);
        }
    }
}
