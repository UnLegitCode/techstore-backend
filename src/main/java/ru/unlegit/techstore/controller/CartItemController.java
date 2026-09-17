package ru.unlegit.techstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.unlegit.techstore.dto.cart.CartItemRequest;
import ru.unlegit.techstore.dto.cart.CartItemUpdateRequest;
import ru.unlegit.techstore.dto.cart.CartResponse;
import ru.unlegit.techstore.dto.info.ErrorResponse;
import ru.unlegit.techstore.service.CartItemService;
import ru.unlegit.techstore.util.AuthenticationUtil;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
@Tag(name = "Корзина", description = "Управление товарами в корзине пользователя")
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping
    @Operation(summary = "Получить содержимое корзины текущего пользователя")
    @ApiResponse(responseCode = "200", description = "Корзина успешно получена")
    public CartResponse getCart(Authentication authentication) {
        return cartItemService.getCart(AuthenticationUtil.currentUserId(authentication));
    }

    @PostMapping
    @Operation(summary = "Добавить товар в корзину")
    @ApiResponse(responseCode = "200", description = "Товар добавлен в корзину")
    @ApiResponse(
            responseCode = "404", description = "Товар не найден",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public CartResponse addItem(
            Authentication authentication,
            @Valid @RequestBody CartItemRequest request
    ) {
        return cartItemService.addItem(AuthenticationUtil.currentUserId(authentication), request);
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Изменить количество товара в корзине")
    @ApiResponse(responseCode = "200", description = "Количество обновлено")
    @ApiResponse(
            responseCode = "404", description = "Позиция корзины не найдена",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public CartResponse updateItemQuantity(
            Authentication authentication,
            @PathVariable int itemId,
            @Valid @RequestBody CartItemUpdateRequest request
    ) {
        return cartItemService.updateItemQuantity(AuthenticationUtil.currentUserId(authentication), itemId, request);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить товар из корзины")
    @ApiResponse(responseCode = "204", description = "Товар удалён из корзины")
    @ApiResponse(
            responseCode = "404", description = "Позиция корзины не найдена",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public void removeItem(Authentication authentication, @PathVariable int itemId) {
        cartItemService.removeItem(AuthenticationUtil.currentUserId(authentication), itemId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Полностью очистить корзину")
    @ApiResponse(responseCode = "204", description = "Корзина очищена")
    public void clearCart(Authentication authentication) {
        cartItemService.clearCart(AuthenticationUtil.currentUserId((authentication)));
    }
}