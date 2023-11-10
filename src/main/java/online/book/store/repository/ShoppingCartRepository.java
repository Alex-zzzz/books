package online.book.store.repository;

import java.util.Optional;
import online.book.store.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = "cartItems")
    ShoppingCart save(ShoppingCart shoppingCart);

    @EntityGraph(attributePaths = "cartItems")
    Optional<ShoppingCart> findByUserEmail(String email);
}
