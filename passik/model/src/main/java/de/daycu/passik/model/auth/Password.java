package de.daycu.passik.model.auth;

/**
 * Defines a contract for objects that represent passwords.
 * This interface provides a standardized way to access the raw (plaintext or encoded)
 * password value associated with a password object.
 */
public interface Password {

    /**
     * Returns the raw password value as a String.
     * The interpretation of "raw" depends on the context. Before encoding,
     * it would be the plaintext password. After retrieval from storage, it
     * would typically be the encoded (hashed) password. Implementations of
     * this interface should provide access to this underlying password string.
     *
     * @return The raw password value.
     */
    String rawPassword();
}
