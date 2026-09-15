package ru.unlegit.techstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.unlegit.techstore.dto.auth.*;
import ru.unlegit.techstore.dto.info.ErrorResponse;
import ru.unlegit.techstore.dto.info.MessageResponse;
import ru.unlegit.techstore.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "Регистрация, вход и восстановление пароля")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(
            responseCode = "201", description = "Пользователь успешно зарегистрирован",
            content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @ApiResponse(
            responseCode = "409", description = "Email уже занят",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "400", description = "Ошибка валидации",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему")
    @ApiResponse(
            responseCode = "200", description = "Успешный вход",
            content = @Content(schema = @Schema(implementation = AuthResponse.class))
    )
    @ApiResponse(
            responseCode = "401", description = "Неверный email или пароль",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/password-reset/request")
    @Operation(
            summary = "Запрос на восстановление пароля",
            description = "Генерирует токен сброса пароля. После подключения email-отправки токен " +
                    "перестанет возвращаться в ответе и будет отправляться письмом."
    )
    @ApiResponse(
            responseCode = "200", description = "Токен сброса пароля сгенерирован",
            content = @Content(schema = @Schema(implementation = MessageResponse.class))
    )
    @ApiResponse(
            responseCode = "400", description = "Ошибка валидации",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "401", description = "Аккаунт не найден",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public MessageResponse requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        String token = authService.requestPasswordReset(request);

        return new MessageResponse("Токен сброса пароля сгенерирован", token);
    }

    @PostMapping("/password-reset/confirm")
    @Operation(summary = "Подтверждение сброса пароля")
    @ApiResponse(
            responseCode = "200", description = "Пароль успешно изменён",
            content = @Content(schema = @Schema(implementation = MessageResponse.class))
    )
    @ApiResponse(
            responseCode = "400", description = "Ошибка валидации",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "401", description = "Невалидный или просроченный токен",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public MessageResponse confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        authService.confirmPasswordReset(request);

        return new MessageResponse("Пароль успешно изменён", null);
    }
}