package kafka.orders.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import kafka.orders.example.dto.OrderRequestDto;
import kafka.orders.example.dto.OrderResponseDto;
import kafka.orders.example.service.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public OrderResponseDto processOrder(@Valid @RequestBody OrderRequestDto request) {
        return service.processOrder(request);
    }
}
