package kafka.orders.example.dto;

public record InventoryEventDto(Long orderId, String status) {
}
