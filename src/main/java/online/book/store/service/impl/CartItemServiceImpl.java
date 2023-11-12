package online.book.store.service.impl;

import lombok.RequiredArgsConstructor;
import online.book.store.dto.CartItemRequestDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.model.Book;
import online.book.store.model.CartItem;
import online.book.store.repository.BookRepository;
import online.book.store.repository.CartItemRepository;
import online.book.store.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(CartItemRequestDto request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + request.getBookId())
        );
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(request.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateQuantityById(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id: " + id)
        );
        cartItem.setId(id);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }
}
