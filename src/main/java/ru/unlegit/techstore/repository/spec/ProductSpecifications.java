package ru.unlegit.techstore.repository.spec;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.unlegit.techstore.dto.product.ProductFilter;
import ru.unlegit.techstore.model.Product;

@UtilityClass
public class ProductSpecifications {

    public static Specification<Product> fromFilter(ProductFilter filter) {
        Specification<Product> spec = Specification.where(Specification.unrestricted());

        if (filter.categoryId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("category").get("id"), filter.categoryId())
            );
        }
        if (filter.minPrice() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice())
            );
        }
        if (filter.maxPrice() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("price"), filter.maxPrice())
            );
        }
        if (filter.minRating() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("rating"), filter.minRating()))
            ;
        }
        if (filter.badge() != null && !filter.badge().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("badge"), filter.badge())
            );
        }
        if (filter.search() != null && !filter.search().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + filter.search().toLowerCase() + "%")
            );
        }

        return spec;
    }
}