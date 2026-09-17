package ru.unlegit.techstore.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.unlegit.techstore.dto.product.ProductResponse;

import java.time.LocalDateTime;

@Schema(description = "Позиция товара в корзине")
public record CartItemResponse(
        @Schema(description = "Идентификатор позиции корзины", example = "1")
        int id,

        @Schema(description = "Товар")
        ProductResponse product,

        @Schema(description = "Количество товара", example = "2")
        int quantity,

        @Schema(description = "Дата добавления в корзину")
        LocalDateTime addedAt
) {}