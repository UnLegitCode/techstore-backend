package ru.unlegit.techstore.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {

    List<CartItem> findByUserId(int userId);

    Optional<CartItem> findByUserIdAndProductId(int userId, int productId);

    Optional<CartItem> findByIdAndUserId(int id, int userId);

    void deleteByUserId(int userId);
}