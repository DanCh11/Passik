package de.daycu.passik.model.auth;

public class PasswordNotCompleteException extends RuntimeException {
    public PasswordNotCompleteException(String message) {
        super(message);
    }
}
