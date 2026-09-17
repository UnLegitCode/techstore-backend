package ru.unlegit.techstore.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Данные для изменения количества товара в корзине")
public record CartItemUpdateRequest(
        @NotNull(message = "Количество обязательно")
        @Min(value = 1, message = "Количество должно быть не меньше 1")
        @Schema(description = "Новое количество товара", example = "3", minimum = "1")
        int quantity
) {}