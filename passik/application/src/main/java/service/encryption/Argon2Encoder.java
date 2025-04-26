package service.encryption;

import de.daycu.passik.model.auth.Password;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Uses Argon2 for password hashing, leveraging Spring Security's implementation.
 * Provides secure encoding and verification of passwords.
 */
public class Argon2Encoder implements PasswordEncoder<Password> {

    private final Argon2PasswordEncoder encoder;

    /**
     * Configures Argon2 with specific parameters for security and performance.
     * Parameters: saltLength=16, hashLength=32, parallelism=1, memoryLimit=65536, iterations=2.
     */
    public Argon2Encoder() {
        final int hashLength = 32;
        final int saltLength = 16;
        final int parallelism = 1;
        final int memoryLimit = 66536;
        final int iterations = 2;
        this.encoder = new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memoryLimit, iterations);
    }

    /**
     * Encodes the raw password using Argon2. Assumes Password has rawPassword() method.
     * @param password The password to encode.
     * @return The Argon2 hashed password.
     * @throws EncryptionException If encoding fails.
     */
    @Override
    public String encode(Password password) throws EncryptionException {
        return encoder.encode(password.rawPassword());
    }

    /**
     * Verifies if the raw password matches the Argon2 encoded password.
     * Assumes Password has rawPassword() method.
     * @param password The password to verify.
     * @param encodedPassword The stored encoded password.
     * @return True if passwords match, false otherwise.
     */
    @Override
    public boolean verify(Password password, String encodedPassword) {
        return encoder.matches(password.rawPassword(), encodedPassword);
    }
}
