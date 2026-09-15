package ru.unlegit.techstore.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {}