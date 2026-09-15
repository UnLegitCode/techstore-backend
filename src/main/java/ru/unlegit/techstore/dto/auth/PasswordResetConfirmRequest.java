package ru.unlegit.techstore.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Подтверждение сброса пароля по токену")
public record PasswordResetConfirmRequest(
        @NotBlank
        @Schema(
                description = "Токен сброса пароля, полученный при запросе восстановления",
                example = "c56a4180-65aa-42ec-a945-5fd21dec0538",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String token,

        @NotBlank
        @Size(min = 8, max = 100)
        @Schema(
                description = "Новый пароль пользователя",
                example = "NewSecurePass456",
                minLength = 8,
                maxLength = 100,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String newPassword
) {}