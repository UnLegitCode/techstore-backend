package ru.unlegit.techstore.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Корзина пользователя")
public record CartResponse(
        @Schema(description = "Позиции товаров в корзине")
        List<CartItemResponse> items,

        @Schema(description = "Общая стоимость корзины", example = "1999.98")
        double totalPrice,

        @Schema(description = "Общее количество товаров в корзине", example = "3")
        int totalQuantity
) {}