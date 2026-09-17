package ru.unlegit.techstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для создания или обновления категории товаров")
public record ProductCategoryRequest(
        @NotBlank(message = "Название категории не может быть пустым")
        @Size(min = 2, max = 255, message = "Название должно быть от 2 до 255 символов")
        @Schema(description = "Название категории", example = "Смартфоны", minLength = 2, maxLength = 255)
        String title,

        @NotBlank(message = "Emoji не может быть пустым")
        @Size(max = 16, message = "Emoji слишком длинный")
        @Schema(description = "Emoji-иконка категории", example = "📱", maxLength = 16)
        String emoji
) {}