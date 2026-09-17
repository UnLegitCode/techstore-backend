package ru.unlegit.techstore.exception.user;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Неверный email или пароль");
    }
}