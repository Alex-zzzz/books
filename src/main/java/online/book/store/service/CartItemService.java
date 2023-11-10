package online.book.store.service;

import online.book.store.dto.CartItemRequestDto;
import online.book.store.model.CartItem;

public interface CartItemService {
    CartItem createCartItem(CartItemRequestDto request);

    CartItem updateQuantityById(Long id, int quantity);
}
