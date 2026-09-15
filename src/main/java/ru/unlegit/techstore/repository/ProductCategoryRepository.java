package ru.unlegit.techstore.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.ProductCategory;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {}