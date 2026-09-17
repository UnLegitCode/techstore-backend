package ru.unlegit.techstore.exception.user;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Пользователь с email '" + email + "' уже существует");
    }
}