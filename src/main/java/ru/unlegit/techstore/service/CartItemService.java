package ru.unlegit.techstore.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unlegit.techstore.dto.cart.CartItemRequest;
import ru.unlegit.techstore.dto.cart.CartItemUpdateRequest;
import ru.unlegit.techstore.dto.cart.CartResponse;
import ru.unlegit.techstore.exception.cart.CartItemNotFoundException;
import ru.unlegit.techstore.exception.product.ProductNotFoundException;
import ru.unlegit.techstore.mapper.CartMapper;
import ru.unlegit.techstore.model.CartItem;
import ru.unlegit.techstore.model.Product;
import ru.unlegit.techstore.model.User;
import ru.unlegit.techstore.repository.CartItemRepository;
import ru.unlegit.techstore.repository.ProductRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {

    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    CartMapper cartMapper;

    @Transactional(readOnly = true)
    public CartResponse getCart(int userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);

        return cartMapper.toResponse(items);
    }

    @Transactional
    public CartResponse addItem(int userId, CartItemRequest request) {
        Product product = findProductOrThrow(request.productId());

        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, request.productId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + request.quantity());
                    return existing;
                })
                .orElseGet(() -> CartItem.builder()
                        .user(referenceUser(userId))
                        .product(product)
                        .quantity(request.quantity())
                        .build());

        cartItemRepository.save(item);

        log.debug("Товар добавлен в корзину: userId={}, productId={}, quantity={}",
                userId, request.productId(), request.quantity()
        );

        return getCart(userId);
    }

    @Transactional
    public CartResponse updateItemQuantity(int userId, int itemId, CartItemUpdateRequest request) {
        CartItem item = findCartItemOrThrow(itemId, userId);
        item.setQuantity(request.quantity());

        cartItemRepository.save(item);

        log.debug("Количество обновлено: userId={}, itemId={}, quantity={}", userId, itemId, request.quantity());

        return getCart(userId);
    }

    @Transactional
    public void removeItem(int userId, int itemId) {
        CartItem item = findCartItemOrThrow(itemId, userId);

        cartItemRepository.delete(item);

        log.debug("Товар удалён из корзины: userId={}, itemId={}", userId, itemId);
    }

    @Transactional
    public void clearCart(int userId) {
        cartItemRepository.deleteByUserId(userId);

        log.debug("Корзина очищена: userId={}", userId);
    }

    private CartItem findCartItemOrThrow(int itemId, int userId) {
        return cartItemRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new CartItemNotFoundException(
                        "Позиция корзины с id=%d не найдена".formatted(itemId)
                ));
    }

    private Product findProductOrThrow(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Товар с id=%d не найден".formatted(productId)
                ));
    }

    private User referenceUser(int userId) {
        User user = new User();

        user.setId(userId);

        return user;
    }
}