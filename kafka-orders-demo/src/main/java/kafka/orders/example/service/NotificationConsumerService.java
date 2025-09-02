package kafka.orders.example.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import kafka.orders.example.dto.InventoryEventDto;
import kafka.orders.example.dto.NotificationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationConsumerService {

    private final KafkaOrderProducerService producer;

    @KafkaListener(topics = "#{@kafkaConfig.getInventoryTopic()}",
            groupId = "#{@kafkaConfig.getNotificationsGroup()}", concurrency = "3")
    public void handleInventory(ConsumerRecord<String, InventoryEventDto> record,
            Acknowledgment ack) {
        InventoryEventDto inventoryDto = record.value();
        try {
            String message = "UPDATED".equals(inventoryDto.status()) ? "Your order is processed!"
                    : "Order failed: out of stock";
            producer.sendNotification(new NotificationEventDto(inventoryDto.orderId(), message));
            log.info("Notification sent for order {}: {}", inventoryDto.orderId(), message);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error sending notification: {}", e);
        }
    }
}
