package online.book.store.mapper;

import online.book.store.config.MapperConfig;
import online.book.store.dto.OrderItemResponseDto;
import online.book.store.model.CartItem;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "orderItem.book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "book", source = "cartItem.book")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "price", source = "cartItem.book.price")
    @Mapping(target = "quantity", source = "cartItem.quantity")
    OrderItem toEntity(CartItem cartItem, Order order);
}
