package service.vault;

/**
 * Helper record to track the isSuccessful or failure of user's authentication.
 *
 * @param isSuccessful if the credentials were saved successful or not
 * @param message output of executed credential save.
 */
public record DigitalAccountDeletionResult(boolean isSuccessful, String message) { }
