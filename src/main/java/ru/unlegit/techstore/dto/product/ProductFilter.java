package ru.unlegit.techstore.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Параметры фильтрации списка товаров")
public record ProductFilter(
        @Schema(description = "Фильтр по категории", example = "1", nullable = true)
        Integer categoryId,

        @Schema(description = "Минимальная цена", example = "100.0", nullable = true)
        Double minPrice,

        @Schema(description = "Максимальная цена", example = "2000.0", nullable = true)
        Double maxPrice,

        @Schema(description = "Минимальный рейтинг", example = "4.0", nullable = true)
        Double minRating,

        @Schema(description = "Фильтр по бейджу (sale/new и т.д.)", example = "sale", nullable = true)
        String badge,

        @Schema(description = "Поиск по названию товара", example = "iPhone", nullable = true)
        String search
) {}