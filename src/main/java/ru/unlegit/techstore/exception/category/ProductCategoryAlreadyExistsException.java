package ru.unlegit.techstore.exception.category;

public class ProductCategoryAlreadyExistsException extends RuntimeException {

    public ProductCategoryAlreadyExistsException(String message) {
        super(message);
    }
}