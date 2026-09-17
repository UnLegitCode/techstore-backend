package ru.unlegit.techstore.exception.user;

public class InvalidOrExpiredTokenException extends RuntimeException {
    
    public InvalidOrExpiredTokenException() {
        super("Токен недействителен или срок его действия истёк");
    }
}