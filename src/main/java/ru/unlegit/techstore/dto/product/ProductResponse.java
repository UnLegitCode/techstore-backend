package ru.unlegit.techstore.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.unlegit.techstore.dto.category.ProductCategoryResponse;

@Schema(description = "Товар")
public record ProductResponse(
        @Schema(description = "Идентификатор товара", example = "1")
        Integer id,

        @Schema(description = "Название товара", example = "iPhone 15 Pro")
        String title,

        @Schema(description = "Категория товара")
        ProductCategoryResponse category,

        @Schema(description = "Цена товара", example = "999.99")
        Double price,

        @Schema(description = "Предыдущая цена (для отображения скидки)", example = "1199.99", nullable = true)
        Double previousPrice,

        @Schema(description = "Рейтинг товара от 1 до 5", example = "4.5")
        Double rating,

        @Schema(description = "Количество отзывов", example = "128")
        Integer reviews,

        @Schema(description = "Emoji-символ товара", example = "📱")
        String emoji,

        @Schema(description = "Бейдж товара (sale/new и т.д.)", example = "sale", nullable = true)
        String badge
) {}