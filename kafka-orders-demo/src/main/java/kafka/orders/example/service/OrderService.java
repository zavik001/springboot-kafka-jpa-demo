package kafka.orders.example.service;

import org.springframework.stereotype.Service;
import kafka.orders.example.dto.OrderRequestDto;
import kafka.orders.example.dto.OrderResponseDto;
import kafka.orders.example.mapper.OrderMapper;
import kafka.orders.example.model.Order;
import kafka.orders.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderResponseDto processOrder(OrderRequestDto request) {
        Order order = mapper.toEntity(request);
        Order savedOrder = repository.save(order);
        return mapper.toDto(savedOrder);
    }
}
