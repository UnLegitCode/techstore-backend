package ru.unlegit.techstore.mapper;

import org.mapstruct.Mapper;
import ru.unlegit.techstore.dto.category.ProductCategoryResponse;
import ru.unlegit.techstore.model.ProductCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryResponse toResponse(ProductCategory category);

    List<ProductCategoryResponse> toResponseList(List<ProductCategory> categories);
}