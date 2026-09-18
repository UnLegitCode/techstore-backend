package ru.unlegit.techstore.mapper;

import org.mapstruct.Mapper;
import ru.unlegit.techstore.dto.cart.CartItemResponse;
import ru.unlegit.techstore.model.CartItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {

    CartItemResponse toResponse(CartItem cartItem);

    List<CartItemResponse> toResponseList(List<CartItem> cartItems);
}