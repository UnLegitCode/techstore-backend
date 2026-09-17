package ru.unlegit.techstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.unlegit.techstore.dto.info.ErrorResponse;
import ru.unlegit.techstore.exception.cart.CartItemNotFoundException;
import ru.unlegit.techstore.exception.category.ProductCategoryAlreadyExistsException;
import ru.unlegit.techstore.exception.category.ProductCategoryInUseException;
import ru.unlegit.techstore.exception.category.ProductCategoryNotFoundException;
import ru.unlegit.techstore.exception.product.ProductNotFoundException;
import ru.unlegit.techstore.exception.user.EmailAlreadyExistsException;
import ru.unlegit.techstore.exception.user.InvalidCredentialsException;
import ru.unlegit.techstore.exception.user.InvalidOrExpiredTokenException;

@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            ProductCategoryAlreadyExistsException.class,
            ProductCategoryInUseException.class
    })
    public ErrorResponse handleConflict(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({InvalidCredentialsException.class, InvalidOrExpiredTokenException.class})
    public ErrorResponse handleUnauthorized(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            ProductCategoryNotFoundException.class,
            ProductNotFoundException.class,
            CartItemNotFoundException.class
    })
    public ErrorResponse handleNotFound(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }
}