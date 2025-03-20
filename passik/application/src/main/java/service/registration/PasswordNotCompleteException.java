package service.registration;

public class PasswordNotCompleteException extends RuntimeException {
    public PasswordNotCompleteException(String message) {
        super(message);
    }
}
