package online.book.store.repository;

import online.book.store.model.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = "book")
    CartItem save(CartItem cartItem);
}
