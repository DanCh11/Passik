package service.encryption;

import de.daycu.passik.model.auth.Password;
import de.daycu.passik.model.vault.CredentialPassword;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * The service to encrypt oncoming passwords in the database, whether is master or credential password.
 */
public class EncryptionService {

    private final Argon2PasswordEncoder encoder;

    /**
     * Instantiates an Argon2 encoder with required parameters.
     */
    public EncryptionService() {
        final int hashLength = 32;
        final int saltLength = 16;
        final int parallelism = 1;
        final int memoryLimit = 66536;
        final int iterations = 2;
        this.encoder = new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memoryLimit, iterations);
    }

    public String encodePassword(Password rawPassword) {
        return encoder.encode(rawPassword.rawPassword());
    }

    public boolean verifyPassword(Password rawPassword, String storedRawPassword) {
        return encoder.matches(rawPassword.rawPassword(), storedRawPassword);
    }

    /**
     * TODO: Make this method generic for Master password as well
     */
    public CredentialPassword encodeCredentialPassword(Password credentialPassword) {
        try {
            return new CredentialPassword(encodePassword(credentialPassword));
        } catch (Exception e) {
            throw new CredentialEncryptionException("Failed to encrypt password", e);
        }
    }
}
