package ru.unlegit.techstore.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {}