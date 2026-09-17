package ru.unlegit.techstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Категория товаров")
public record ProductCategoryResponse(
        @Schema(description = "Идентификатор категории", example = "1")
        int id,

        @Schema(description = "Название категории", example = "Смартфоны")
        String title,

        @Schema(description = "Emoji-иконка категории", example = "📱")
        String emoji

) {}