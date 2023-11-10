package online.book.store.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import online.book.store.config.MapperConfig;
import online.book.store.dto.CartItemResponseDto;
import online.book.store.dto.ShoppingCartResponseDto;
import online.book.store.model.CartItem;
import online.book.store.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "shoppingCart.user.id")
    @Mapping(target = "cartItems",
            source = "shoppingCart.cartItems",
            qualifiedByName = "changeCartItems")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @Named("changeCartItems")
    default Set<CartItemResponseDto> changeCartItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    return new CartItemResponseDto(
                            cartItem.getId(),
                            cartItem.getBook().getId(),
                            cartItem.getBook().getTitle(),
                            cartItem.getQuantity());
                })
                .collect(Collectors.toSet());
    }
}
