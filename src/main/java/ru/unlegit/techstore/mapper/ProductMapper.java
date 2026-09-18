package ru.unlegit.techstore.mapper;

import org.mapstruct.Mapper;
import ru.unlegit.techstore.dto.product.ProductResponse;
import ru.unlegit.techstore.model.Product;

@Mapper(componentModel = "spring", uses = ProductCategoryMapper.class)
public interface ProductMapper {

    ProductResponse toResponse(Product product);
}