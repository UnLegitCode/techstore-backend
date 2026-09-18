package ru.unlegit.techstore.mapper;

import org.mapstruct.Mapper;
import ru.unlegit.techstore.dto.cart.CartItemResponse;
import ru.unlegit.techstore.dto.cart.CartResponse;
import ru.unlegit.techstore.model.CartItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public abstract class CartMapper {

    protected abstract List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems);

    public CartResponse toResponse(List<CartItem> cartItems) {
        return new CartResponse(
                toCartItemResponseList(cartItems),
                calculateTotalPrice(cartItems),
                calculateTotalQuantity(cartItems)
        );
    }

    private double calculateTotalPrice(List<CartItem> items) {
        return items.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();
    }

    private int calculateTotalQuantity(List<CartItem> items) {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}