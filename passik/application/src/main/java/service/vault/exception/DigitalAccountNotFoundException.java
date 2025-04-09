package service.vault.exception;

public class DigitalAccountNotFoundException extends RuntimeException {
    public DigitalAccountNotFoundException(String message) {
        super(message);
    }
}
