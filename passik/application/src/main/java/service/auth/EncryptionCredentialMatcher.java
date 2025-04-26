package service.auth;

import de.daycu.passik.model.auth.MasterPassword;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import service.encryption.EncryptionService;

/**
 * A custom Shiro {@link CredentialsMatcher} that delegates password verification
 * to the application's {@link EncryptionService}. This allows Shiro to leverage
 * the specific password hashing algorithm and verification logic configured
 * within the {@code EncryptionService}.
 */
@AllArgsConstructor
public class EncryptionCredentialMatcher implements CredentialsMatcher {

    private EncryptionService encryptionService;

    /**
     * Performs the credential matching for authentication.
     * Extracts the raw password from the authentication token and the stored
     * encoded password from the authentication information. It then uses the
     * {@link EncryptionService} to verify if the raw password matches the
     * stored encoded password.
     *
     * @param token The authentication token provided by the user, containing their submitted credentials (password).
     * @param info The authentication information retrieved for the user, containing their stored credentials (hashed password).
     * @return {@code true} if the submitted password matches the stored encoded password, {@code false} otherwise.
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String rawPassword = new String((char[]) token.getCredentials());
        String storedEncodedPassword = (String) info.getCredentials();

        return encryptionService.verifyPassword(new MasterPassword(rawPassword), storedEncodedPassword);
    }
}
