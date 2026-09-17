package ru.unlegit.techstore.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Данные для создания или обновления товара")
public record ProductRequest(
        @NotBlank(message = "Название товара не может быть пустым")
        @Size(min = 2, max = 255, message = "Название должно быть от 2 до 255 символов")
        @Schema(description = "Название товара", example = "iPhone 15 Pro", minLength = 2, maxLength = 255)
        String title,

        @NotNull(message = "Категория обязательна")
        @Schema(description = "Идентификатор категории товара", example = "1")
        @Positive(message = "Идентификатор категории должен быть положительным")
        Integer categoryId,

        @NotNull(message = "Цена обязательна")
        @Schema(description = "Цена товара", example = "999.99")
        @PositiveOrZero(message = "Цена не может быть отрицательной")
        Double price,

        @PositiveOrZero(message = "Предыдущая цена не может быть отрицательной")
        @Schema(
                description = "Предыдущая цена (для отображения скидки), опционально",
                example = "1199.99", nullable = true
        )
        Double previousPrice,

        @NotNull(message = "Рейтинг обязателен")
        @DecimalMin(value = "1.0", message = "Рейтинг не может быть меньше 1.0")
        @DecimalMax(value = "5.0", message = "Рейтинг не может быть больше 5.0")
        @Schema(description = "Рейтинг товара от 1 до 5", example = "4.5", minimum = "1.0", maximum = "5.0")
        Double rating,

        @NotNull(message = "Количество отзывов обязательно")
        @Schema(description = "Количество отзывов", example = "128")
        @PositiveOrZero(message = "Количество отзывов не может быть отрицательным")
        Integer reviews,

        @NotBlank(message = "Emoji не может быть пустым")
        @Size(max = 16, message = "Emoji слишком длинный")
        @Schema(description = "Emoji-символ товара", example = "📱", maxLength = 16)
        String emoji,

        @Size(max = 50, message = "Бейдж слишком длинный")
        @Schema(description = "Бейдж товара (sale/new и т.д.), опционально", example = "sale", nullable = true)
        String badge
) {}