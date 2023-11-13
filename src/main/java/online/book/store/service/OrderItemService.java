package online.book.store.service;

import java.util.List;
import online.book.store.dto.OrderItemResponseDto;
import online.book.store.model.CartItem;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    OrderItem createOrderItemFromCartItemAndOrder(CartItem cartItem, Order order);

    List<OrderItemResponseDto> getAllByOrderId(Pageable pageable, Long orderId);

    OrderItemResponseDto getByOrderIdAndItemId(Long orderId, Long itemId);
}
