package service.vault.exception;

public class CredentialPersistenceException extends RuntimeException {
    public CredentialPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
