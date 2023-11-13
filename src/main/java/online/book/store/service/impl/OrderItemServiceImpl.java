package online.book.store.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import online.book.store.dto.OrderItemResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.OrderItemMapper;
import online.book.store.model.CartItem;
import online.book.store.model.Order;
import online.book.store.model.OrderItem;
import online.book.store.repository.OrderItemRepository;
import online.book.store.service.OrderItemService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItem createOrderItemFromCartItemAndOrder(CartItem cartItem, Order order) {
        OrderItem orderItem = orderItemMapper.toEntity(cartItem, order);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItemResponseDto> getAllByOrderId(Pageable pageable, Long orderId) {
        return orderItemRepository.findAllByOrderId(pageable, orderId).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponseDto getByOrderIdAndItemId(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndId(orderId, itemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order item by order id: "
                        + orderId + ", and item id: " + itemId)
        );
        return orderItemMapper.toDto(orderItem);
    }
}
