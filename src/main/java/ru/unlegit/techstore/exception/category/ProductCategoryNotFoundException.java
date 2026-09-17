package ru.unlegit.techstore.exception.category;

public class ProductCategoryNotFoundException extends RuntimeException {

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}