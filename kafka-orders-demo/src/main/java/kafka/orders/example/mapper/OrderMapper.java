package kafka.orders.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import kafka.orders.example.dto.OrderRequestDto;
import kafka.orders.example.dto.OrderResponseDto;
import kafka.orders.example.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequestDto dto);

    @Mapping(target = "id", source = "id")
    OrderResponseDto toDto(Order entity);
}
