package ru.unlegit.techstore.exception;

public class InvalidOrExpiredTokenException extends RuntimeException {
    
    public InvalidOrExpiredTokenException() {
        super("Токен недействителен или срок его действия истёк");
    }
}