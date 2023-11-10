package online.book.store.service;

import lombok.RequiredArgsConstructor;
import online.book.store.UserService;
import online.book.store.dto.CartItemRequestDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.exception.EntityNotFoundException;
import online.book.store.mapper.ShoppingCartMapper;
import online.book.store.model.CartItem;
import online.book.store.model.ShoppingCart;
import online.book.store.model.User;
import online.book.store.repository.CartItemRepository;
import online.book.store.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto addCartItemToShoppingCart(
            CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemService.createCartItem(cartItemRequestDto);
        ShoppingCart shoppingCart = getShoppingCart();
        shoppingCart.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponseDto getUserShoppingCart() {
        return shoppingCartMapper.toDto(getShoppingCart());
    }

    @Override
    public ShoppingCartResponseDto updateQuantityFromCartItemById(Long id, int quantity) {
        cartItemService.updateQuantityById(id, quantity);
        return getUserShoppingCart();
    }

    @Override
    public void deleteBookFromShoppingCartById(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find cart item by id: " + id);
        }

        cartItemRepository.deleteById(id);
    }

    @Override
    public ShoppingCart getShoppingCart() {
        User user = userService.getAuthenticatedUser();
        return shoppingCartRepository.findByUserEmail(
                user.getEmail()).orElseThrow(() -> new EntityNotFoundException(
                "Can't find shopping cart by user: " + user)
        );
    }
}
