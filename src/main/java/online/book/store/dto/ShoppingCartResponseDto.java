package online.book.store.dto;

import java.util.Set;

public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        Set<CartItemResponseDto> cartItems
) {
}
