package service.encryption;

import de.daycu.passik.model.auth.Password;

/**
 * Defines the contract for encoding (hashing) and verifying passwords.
 * Implementations of this interface provide specific algorithms for securely
 * transforming plaintext passwords into a hashed format and for comparing
 * a plaintext password against an encoded (hashed) password.
 *
 * @param <T> The specific type of {@link Password} this encoder works with.
 * This allows for type-safe encoding and verification based on
 * different password representations within the application.
 */
public interface PasswordEncoder<T extends Password> {
    /**
     * Encodes the given plaintext password.
     *
     * Implementations should use a secure hashing algorithm (e.g., bcrypt, Argon2, scrypt)
     * and typically include a salt to protect against rainbow table attacks.
     * The encoded password should be a String representation of the hashed value,
     * often including the salt and algorithm identifier.
     *
     * @param password The plaintext password to encode. The type is constrained
     * by the generic parameter {@code T}, ensuring type safety.
     * @return The encoded (hashed) representation of the password.
     */
    String encode(T password);

    /**
     * Verifies if the given plaintext password matches the encoded password.
     *
     * Implementations should take the plaintext password and the stored encoded
     * password as input and return {@code true} if they match (after encoding
     * the plaintext password using the same algorithm and salt used to generate
     * the stored encoded password), and {@code false} otherwise.
     *
     * @param password The plaintext password to verify. The type is constrained
     * by the generic parameter {@code T}.
     * @param encodedPassword The encoded (hashed) password to compare against.
     * @return {@code true} if the plaintext password matches the encoded password,
     * {@code false} otherwise.
     */
    boolean verify(T password, String encodedPassword);
}
