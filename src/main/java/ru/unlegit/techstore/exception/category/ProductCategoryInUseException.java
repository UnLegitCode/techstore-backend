package ru.unlegit.techstore.exception.category;

public class ProductCategoryInUseException extends RuntimeException {

    public ProductCategoryInUseException(String message) {
        super(message);
    }
}