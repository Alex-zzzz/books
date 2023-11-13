package online.book.store.repository;

import java.util.List;
import java.util.Optional;
import online.book.store.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = "book")
    OrderItem save(OrderItem orderItem);

    @EntityGraph(attributePaths = "book")
    List<OrderItem> findAllByOrderId(Pageable pageable, Long id);

    @EntityGraph(attributePaths = "book")
    Optional<OrderItem> findByOrderIdAndId(Long orderId, Long id);
}
