package online.book.store.mapper;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import online.book.store.config.MapperConfig;
import online.book.store.dto.OrderItemResponseDto;
import online.book.store.dto.OrderRequestDto;
import online.book.store.dto.OrderResponseDto;
import online.book.store.model.Book;
import online.book.store.model.CartItem;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import online.book.store.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "order.user.id")
    @Mapping(target = "status",
            source = "order.status",
            qualifiedByName = "getStatus")
    @Mapping(target = "orderItems",
            source = "order.orderItems",
            qualifiedByName = "changeOrderItemToDto")
    OrderResponseDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", expression = "java(online.book.store.model.Order.Status.PENDING)")
    @Mapping(target = "total", source = "shoppingCart.cartItems", qualifiedByName = "countTotal")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    Order toEntity(OrderRequestDto request, ShoppingCart shoppingCart);

    @Named("changeOrderItemToDto")
    default Set<OrderItemResponseDto> changeOrderItemToDto(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item ->
                        new OrderItemResponseDto(
                                item.getId(),
                                item.getBook().getId(),
                                item.getQuantity())
                )
                .collect(Collectors.toSet());
    }

    @Named("getStatus")
    default String getStatus(Order.Status status) {
        return status.name();
    }

    @Named("countTotal")
    default BigDecimal countTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getBook)
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
