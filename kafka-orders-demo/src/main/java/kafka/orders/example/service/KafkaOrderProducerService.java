package kafka.orders.example.service;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import kafka.orders.example.config.KafkaConfig;
import kafka.orders.example.dto.InventoryEventDto;
import kafka.orders.example.dto.NotificationEventDto;
import kafka.orders.example.dto.OrderResponseDto;
import kafka.orders.example.dto.PaymentEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaOrderProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    public void sendOrder(OrderResponseDto orderDto) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate
                .send(kafkaConfig.getOrdersTopic(), orderDto.id().toString(), orderDto);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending order: {}", ex);
            } else {
                log.info("Sent order to {}: offset {}", result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    public void sendPayment(PaymentEventDto paymentDto) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate
                .send(kafkaConfig.getPaymentsTopic(), paymentDto.orderId().toString(), paymentDto);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending payment: {}", ex);
            } else {
                log.info("Sent payment to {}: offset {}", result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    public void sendInventory(InventoryEventDto inventoryDto) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(
                kafkaConfig.getInventoryTopic(), inventoryDto.orderId().toString(), inventoryDto);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending inventory: {}", ex);
            } else {
                log.info("Sent inventory to {}: offset {}", result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            }
        });
    }

    public void sendNotification(NotificationEventDto notificationDto) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaConfig.getNotificationsTopic(),
                        notificationDto.orderId().toString(), notificationDto);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending notification: {}", ex);
            } else {
                log.info("Sent notification to {}: offset {}", result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}
