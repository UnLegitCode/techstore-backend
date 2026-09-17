package ru.unlegit.techstore.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unlegit.techstore.dto.category.ProductCategoryRequest;
import ru.unlegit.techstore.dto.category.ProductCategoryResponse;
import ru.unlegit.techstore.exception.category.ProductCategoryAlreadyExistsException;
import ru.unlegit.techstore.exception.category.ProductCategoryInUseException;
import ru.unlegit.techstore.exception.category.ProductCategoryNotFoundException;
import ru.unlegit.techstore.model.ProductCategory;
import ru.unlegit.techstore.repository.ProductCategoryRepository;
import ru.unlegit.techstore.repository.ProductRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCategoryService {

    ProductCategoryRepository productCategoryRepository;
    ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getAllCategories() {
        return productCategoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductCategoryResponse getCategoryById(Integer id) {
        ProductCategory category = findCategoryOrThrow(id);

        return mapToResponse(category);
    }

    @Transactional
    public ProductCategoryResponse createCategory(ProductCategoryRequest request) {
        if (productCategoryRepository.existsByTitle(request.title())) {
            throw new ProductCategoryAlreadyExistsException(
                    "Категория с названием \"%s\" уже существует".formatted(request.title())
            );
        }

        ProductCategory category = ProductCategory.builder()
                .title(request.title())
                .emoji(request.emoji())
                .build();

        category = productCategoryRepository.save(category);

        log.debug("Категория создана: {} (id={})", category.getTitle(), category.getId());

        return mapToResponse(category);
    }

    @Transactional
    public ProductCategoryResponse updateCategory(Integer id, ProductCategoryRequest request) {
        ProductCategory category = findCategoryOrThrow(id);

        productCategoryRepository.findByTitle(request.title()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ProductCategoryAlreadyExistsException(
                        String.format("Категория с названием \"%s\" уже существует", request.title())
                );
            }
        });

        category.setTitle(request.title());
        category.setEmoji(request.emoji());

        category = productCategoryRepository.save(category);

        log.debug("Категория обновлена: id={}", id);

        return mapToResponse(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        ProductCategory category = findCategoryOrThrow(id);

        long productsCount = productRepository.countByCategoryId(id);
        if (productsCount > 0) {
            throw new ProductCategoryInUseException(
                    String.format("Нельзя удалить категорию: в ней ещё %d товар(ов)", productsCount)
            );
        }

        productCategoryRepository.delete(category);

        log.debug("Категория удалена: id={}", id);
    }

    private ProductCategory findCategoryOrThrow(Integer id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryNotFoundException(
                        "Категория с id=%d не найдена".formatted(id)
                ));
    }

    private ProductCategoryResponse mapToResponse(ProductCategory category) {
        return new ProductCategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getEmoji()
        );
    }
}