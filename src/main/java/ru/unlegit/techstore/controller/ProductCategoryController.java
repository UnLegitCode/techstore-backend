package ru.unlegit.techstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.unlegit.techstore.dto.category.ProductCategoryRequest;
import ru.unlegit.techstore.dto.category.ProductCategoryResponse;
import ru.unlegit.techstore.dto.info.ErrorResponse;
import ru.unlegit.techstore.service.ProductCategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Категории товаров", description = "Управление категориями техники")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    @PreAuthorize("permitAll()")
    @Operation(summary = "Получить список всех категорий")
    @ApiResponse(responseCode = "200", description = "Список категорий успешно получен")
    public List<ProductCategoryResponse> getAllCategories() {
        return productCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Получить категорию по ID")
    @ApiResponse(responseCode = "200", description = "Категория найдена")
    @ApiResponse(
            responseCode = "404", description = "Категория не найдена",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ProductCategoryResponse getCategoryById(
            @Schema(description = "Идентификатор категории", example = "1")
            @PathVariable int id
    ) {
        return productCategoryService.getCategoryById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новую категорию")
    @ApiResponse(responseCode = "201", description = "Категория создана")
    @ApiResponse(
            responseCode = "409", description = "Категория с таким названием уже существует",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ProductCategoryResponse createCategory(@Valid @RequestBody ProductCategoryRequest request) {
        return productCategoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить категорию")
    @ApiResponse(responseCode = "200", description = "Категория обновлена")
    @ApiResponse(
            responseCode = "404", description = "Категория не найдена",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "409", description = "Категория с таким названием уже существует",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public ProductCategoryResponse updateCategory(
            @PathVariable int id,
            @Valid @RequestBody ProductCategoryRequest request
    ) {
        return productCategoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить категорию")
    @ApiResponse(responseCode = "204", description = "Категория удалена")
    @ApiResponse(
            responseCode = "404", description = "Категория не найдена",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "409", description = "Категория используется товарами",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    public void deleteCategory(@PathVariable int id) {
        productCategoryService.deleteCategory(id);
    }
}