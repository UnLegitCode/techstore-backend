package ru.unlegit.techstore.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Результат успешной аутентификации")
public record AuthResponse(
        @Schema(description = "Идентификатор пользователя", example = "42")
        Integer userId,

        @Schema(description = "Email пользователя", example = "user@example.com")
        String email,

        @Schema(
                description = "JWT-токен доступа",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIn0.abc123signature"
        )
        String token
) {}