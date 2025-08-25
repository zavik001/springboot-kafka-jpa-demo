package kafka.orders.example.dto;

public record OrderRequestDto(String customerName, Long amount) {
}
