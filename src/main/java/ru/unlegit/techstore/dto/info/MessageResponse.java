package ru.unlegit.techstore.dto.info;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сообщение об успешном выполнении операции")
public record MessageResponse(
        @Schema(description = "Текст сообщения", example = "Пароль успешно изменён")
        String message,

        @Schema(
                description = "Токен сброса пароля (временно, до подключения email-отправки)",
                example = "c56a4180-65aa-42ec-a945-5fd21dec0538",
                nullable = true
        )
        String token
) {}