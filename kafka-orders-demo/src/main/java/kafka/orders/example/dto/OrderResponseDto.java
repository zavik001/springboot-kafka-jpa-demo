package kafka.orders.example.dto;

public record OrderResponseDto(Long id, String customerName, Long amount) {
}
