package ru.unlegit.techstore.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Данные для добавления товара в корзину")
public record CartItemRequest(
        @NotNull(message = "Идентификатор товара обязателен")
        @Schema(description = "Идентификатор товара", example = "1")
        @Positive(message = "Идентификатор товара должен быть положительным")
        int productId,

        @NotNull(message = "Количество обязательно")
        @Schema(description = "Количество товара", example = "2", minimum = "1")
        @Min(value = 1, message = "Количество должно быть не меньше 1")
        int quantity
) {}