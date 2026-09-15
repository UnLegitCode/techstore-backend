package ru.unlegit.techstore.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на восстановление пароля")
public record PasswordResetRequest(
        @Email
        @NotBlank
        @Schema(
                description = "Email пользователя, на который зарегистрирован аккаунт",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email
) {}