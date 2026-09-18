package ru.unlegit.techstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.unlegit.techstore.dto.info.ErrorResponse;
import ru.unlegit.techstore.dto.product.ProductFilter;
import ru.unlegit.techstore.dto.product.ProductRequest;
import ru.unlegit.techstore.dto.product.ProductResponse;
import ru.unlegit.techstore.service.ProductService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Товары", description = "Управление товарами магазина")
public class ProductController {

    private static final int PAGE_SIZE = 20;

    private final ProductService productService;

    @GetMapping
    @PreAuthorize("permitAll()")
    @ApiResponse(responseCode = "200", description = "Список товаров успешно получен")
    @Operation(summary = "Получить список товаров с фильтрацией, сортировкой и пагинацией")
    public Page<ProductResponse> getAllProducts(
            @Parameter(description = "Фильтр по категории") @RequestParam(required = false) Integer categoryId,
            @Parameter(description = "Минимальная цена") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Максимальная цена") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Минимальный рейтинг") @RequestParam(required = false) Double minRating,
            @Parameter(description = "Фильтр по бейджу") @RequestParam(required = false) String badge,
            @Parameter(description = "Поиск по названию") @RequestParam(required = false) String search,
            @Parameter(description = "Страница") @RequestParam(required = false, defaultValue = "1") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        ProductFilter filter = new ProductFilter(categoryId, minPrice, maxPrice, minRating, badge, search);

        return productService.getAllProducts(filter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Получить товар по ID")
    @ApiResponse(responseCode = "200", description = "Товар найден")
    @ApiResponse(
            responseCode = "404", description = "Товар не найден",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ProductResponse getProductById(
            @Schema(description = "Идентификатор товара", example = "1")
            @PathVariable int id
    ) {
        return productService.getProductById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый товар")
    @ApiResponse(responseCode = "201", description = "Товар создан")
    @ApiResponse(
            responseCode = "404", description = "Категория не найдена",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить товар")
    @ApiResponse(responseCode = "200", description = "Товар обновлён")
    @ApiResponse(
            responseCode = "404", description = "Товар или категория не найдены",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ProductResponse updateProduct(
            @PathVariable int id,
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить товар")
    @ApiResponse(responseCode = "204", description = "Товар удалён")
    @ApiResponse(
            responseCode = "404", description = "Товар не найден",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }
}