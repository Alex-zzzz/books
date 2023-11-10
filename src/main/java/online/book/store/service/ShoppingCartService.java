package online.book.store.service;

import online.book.store.dto.CartItemRequestDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartResponseDto addCartItemToShoppingCart(CartItemRequestDto cartItemRequestDto);

    ShoppingCartResponseDto getUserShoppingCart();

    ShoppingCartResponseDto updateQuantityFromCartItemById(Long id, int quantity);

    void deleteBookFromShoppingCartById(Long id);

    ShoppingCart getShoppingCart();
}
