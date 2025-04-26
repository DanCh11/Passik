package service.encryption;

import de.daycu.passik.model.auth.Password;
import lombok.AllArgsConstructor;


/**
 * Service responsible for password encryption and verification.
 * Delegates the actual encoding and verification to a configured {@link PasswordEncoder}.
 */
@AllArgsConstructor
public class EncryptionService {
    private final PasswordEncoder<? super Password> passwordEncoder;

    /**
     * Encodes the given password using the configured {@link PasswordEncoder}.
     * @param password The password to encode.
     * @return The encoded password.
     */
    public String encodePassword(Password password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Verifies if the provided password matches the encoded password using the configured {@link PasswordEncoder}.
     * @param password The password to verify.
     * @param encodedPassword The encoded password to compare against.
     * @return True if the password matches the encoded password, false otherwise.
     */
    public boolean verifyPassword(Password password, String encodedPassword) {
        return passwordEncoder.verify(password, encodedPassword);
    }
}
