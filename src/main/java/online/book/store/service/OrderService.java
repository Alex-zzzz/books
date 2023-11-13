package online.book.store.service;

import java.util.List;
import online.book.store.dto.OrderRequestDto;
import online.book.store.dto.OrderResponseDto;
import online.book.store.dto.OrderUpdateStatusDto;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto request);

    List<OrderResponseDto> getAll(Pageable pageable);

    void updateOrderStatus(Long orderId, OrderUpdateStatusDto request);
}
