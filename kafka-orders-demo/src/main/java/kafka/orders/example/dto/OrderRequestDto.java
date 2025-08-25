package kafka.orders.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(
        @NotBlank(message = "Customer name must not be blank") String customerName,
        @NotNull(message = "Amount must not be null") @Min(value = 1,
                message = "Amount must be at least 1") Long amount) {
}
