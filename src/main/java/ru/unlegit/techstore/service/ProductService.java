package ru.unlegit.techstore.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unlegit.techstore.dto.category.ProductCategoryResponse;
import ru.unlegit.techstore.dto.product.ProductFilter;
import ru.unlegit.techstore.dto.product.ProductRequest;
import ru.unlegit.techstore.dto.product.ProductResponse;
import ru.unlegit.techstore.exception.category.ProductCategoryNotFoundException;
import ru.unlegit.techstore.exception.product.ProductNotFoundException;
import ru.unlegit.techstore.model.Product;
import ru.unlegit.techstore.model.ProductCategory;
import ru.unlegit.techstore.repository.ProductCategoryRepository;
import ru.unlegit.techstore.repository.ProductRepository;
import ru.unlegit.techstore.repository.spec.ProductSpecifications;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(ProductFilter filter, Pageable pageable) {
        return productRepository
                .findAll(ProductSpecifications.fromFilter(filter), pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(int id) {
        Product product = findProductOrThrow(id);

        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        ProductCategory category = findCategoryOrThrow(request.categoryId());

        Product product = Product.builder()
                .title(request.title())
                .category(category)
                .price(request.price())
                .previousPrice(request.previousPrice())
                .rating(request.rating())
                .reviews(request.reviews())
                .emoji(request.emoji())
                .badge(request.badge())
                .build();

        product = productRepository.save(product);

        log.debug("Товар создан: {} (id={})", product.getTitle(), product.getId());

        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(int id, ProductRequest request) {
        Product product = findProductOrThrow(id);
        ProductCategory category = findCategoryOrThrow(request.categoryId());

        product.setTitle(request.title());
        product.setCategory(category);
        product.setPrice(request.price());
        product.setPreviousPrice(request.previousPrice());
        product.setRating(request.rating());
        product.setReviews(request.reviews());
        product.setEmoji(request.emoji());
        product.setBadge(request.badge());

        product = productRepository.save(product);

        log.debug("Товар обновлён: id={}", id);

        return mapToResponse(product);
    }

    @Transactional
    public void deleteProduct(int id) {
        Product product = findProductOrThrow(id);

        productRepository.delete(product);

        log.debug("Товар удалён: id={}", id);
    }

    private Product findProductOrThrow(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Товар с id=%d не найден".formatted(id)
                ));
    }

    private ProductCategory findCategoryOrThrow(int categoryId) {
        return productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ProductCategoryNotFoundException(
                        "Категория с id=%d не найдена".formatted(categoryId)
                ));
    }

    private ProductResponse mapToResponse(Product product) {
        ProductCategory category = product.getCategory();
        ProductCategoryResponse categoryResponse = new ProductCategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getEmoji()
        );

        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                categoryResponse,
                product.getPrice(),
                product.getPreviousPrice(),
                product.getRating(),
                product.getReviews(),
                product.getEmoji(),
                product.getBadge()
        );
    }
}