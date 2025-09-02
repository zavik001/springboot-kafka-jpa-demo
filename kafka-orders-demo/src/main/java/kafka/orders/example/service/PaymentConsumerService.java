package kafka.orders.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import kafka.orders.example.config.KafkaConfig;
import kafka.orders.example.dto.OrderResponseDto;
import kafka.orders.example.dto.PaymentEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentConsumerService {

    private final KafkaOrderProducerService producer;
    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "#{@kafkaConfig.getOrdersTopic()}",
            groupId = "#{@kafkaConfig.getPaymentsGroup()}", concurrency = "3")
    public void handleOrder(ConsumerRecord<String, OrderResponseDto> record, Acknowledgment ack) {
        OrderResponseDto orderDto = record.value();
        try {
            // Payment simulation
            if (Math.random() > 0.2) { // 80% success
                producer.sendPayment(
                        new PaymentEventDto(orderDto.id(), "SUCCESS", orderDto.amount()));
                log.info("Payment success for order {}", orderDto.id());
            } else {
                kafkaTemplate.send(kafkaConfig.getDlqTopic(), orderDto.id().toString(), orderDto);
                log.warn("Payment failed, sent to DLQ: {}", orderDto.id());
            }
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing payment: {}", e.getMessage());
        }
    }
}
