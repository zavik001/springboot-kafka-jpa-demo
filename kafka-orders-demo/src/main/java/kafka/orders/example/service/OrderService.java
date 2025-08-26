package kafka.orders.example.service;

import org.springframework.stereotype.Service;
import kafka.orders.example.dto.OrderRequestDto;
import kafka.orders.example.dto.OrderResponseDto;
import kafka.orders.example.mapper.OrderMapper;
import kafka.orders.example.model.Order;
import kafka.orders.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final KafkaOrderProducerService kafkaProducer;
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderResponseDto processOrder(OrderRequestDto request) {
        Order order = mapper.toEntity(request);
        Order savedOrder = repository.save(order);
        log.info("saved order: {}", savedOrder);
        OrderResponseDto response = mapper.toDto(savedOrder);

        kafkaProducer.sendOrder(response);

        return response;
    }
}
