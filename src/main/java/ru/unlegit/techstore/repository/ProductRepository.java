package ru.unlegit.techstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import ru.unlegit.techstore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    long countByCategoryId(int id);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
}