package ru.unlegit.techstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.unlegit.techstore.dto.info.ErrorResponse;
import ru.unlegit.techstore.exception.EmailAlreadyExistsException;
import ru.unlegit.techstore.exception.InvalidCredentialsException;
import ru.unlegit.techstore.exception.InvalidOrExpiredTokenException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ErrorResponse handleEmailExists(EmailAlreadyExistsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorResponse handleInvalidCredentials(InvalidCredentialsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidOrExpiredTokenException.class)
    public ErrorResponse handleInvalidOrExpiredToken(InvalidOrExpiredTokenException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}