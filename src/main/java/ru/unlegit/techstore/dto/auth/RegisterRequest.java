package ru.unlegit.techstore.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для регистрации нового пользователя")
public record RegisterRequest(
        @Email
        @NotBlank
        @Schema(
                description = "Email пользователя, используется как логин",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @NotBlank
        @Size(min = 8, max = 100)
        @Schema(
                description = "Пароль пользователя",
                example = "SecurePass123",
                minLength = 8,
                maxLength = 100,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password
) { }