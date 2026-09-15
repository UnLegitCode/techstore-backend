package ru.unlegit.techstore.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Данные для входа в систему")
public record LoginRequest(
        @Email
        @NotBlank
        @Schema(
                description = "Email пользователя",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @NotBlank
        @Schema(
                description = "Пароль пользователя",
                example = "SecurePass123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password
) {}