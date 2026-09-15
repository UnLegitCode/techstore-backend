package ru.unlegit.techstore.dto.info;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Стандартный формат ошибки")
public record ErrorResponse(
        @Schema(
                description = "Сообщение об ошибке",
                example = "Пользователь с email 'user@example.com' уже существует"
        )
        String error
) {}