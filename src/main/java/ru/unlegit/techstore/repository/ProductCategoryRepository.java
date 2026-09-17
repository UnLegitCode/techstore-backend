package ru.unlegit.techstore.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {

    @NonNull
    @Override
    List<ProductCategory> findAll();

    Optional<ProductCategory> findByTitle(String title);

    boolean existsByTitle(String title);
}