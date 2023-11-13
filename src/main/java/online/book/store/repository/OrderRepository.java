package online.book.store.repository;

import java.util.List;
import java.util.Optional;
import online.book.store.model.Order;
import online.book.store.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "user")
    Order save(Order order);

    @EntityGraph(attributePaths = "orderItems")
    List<Order> findAllByUser(Pageable pageable, User user);

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findById(Long id);
}
