package kafka.orders.example.dto;

public record NotificationEventDto(Long orderId, String message) {
}
