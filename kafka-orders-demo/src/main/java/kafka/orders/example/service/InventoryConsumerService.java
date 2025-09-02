package kafka.orders.example.service;

import java.util.Optional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import kafka.orders.example.dto.InventoryEventDto;
import kafka.orders.example.dto.PaymentEventDto;
import kafka.orders.example.model.Inventory;
import kafka.orders.example.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryConsumerService {

    private final KafkaOrderProducerService producer;
    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "#{kafkaConfig.getPaymentsTopic()}",
            groupId = "#{kafkaConfig.getInventoryGroup()}", concurrency = "3")
    @Transactional
    public void handlePayment(ConsumerRecord<String, PaymentEventDto> record, Acknowledgment ack) {
        PaymentEventDto paymentDto = record.value();
        try {
            if ("SUCCESS".equals(paymentDto.status())) {
                Optional<Inventory> optionalInventory =
                        inventoryRepository.findByItemName("default");
                Inventory inventory = optionalInventory.orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setItemName("default");
                    newInventory.setStock(1000L);
                    return newInventory;
                });

                if (inventory.getStock() >= paymentDto.amount()) {
                    inventory.setStock(inventory.getStock() - paymentDto.amount());
                    inventoryRepository.save(inventory);
                    producer.sendInventory(new InventoryEventDto(paymentDto.orderId(), "UPDATED"));
                    log.info("Inventory updated for order {}", paymentDto.orderId());
                } else {
                    producer.sendInventory(
                            new InventoryEventDto(paymentDto.orderId(), "OUT_OF_STOCK"));
                    log.warn("Out of stock for order {}", paymentDto.orderId());
                }
            }
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing inventory: {}", e.getMessage());
        }
    }
}
