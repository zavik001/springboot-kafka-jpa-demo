package kafka.orders.example.dto;

public record PaymentEventDto(Long orderId, String status, Long amount) {
}
