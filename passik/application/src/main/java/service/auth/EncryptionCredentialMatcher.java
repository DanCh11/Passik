package service.auth;

import de.daycu.passik.model.auth.MasterPassword;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import service.encryption.EncryptionService;

/**
 * Verifies if given password and stored master password matches.
 */
@AllArgsConstructor
public class EncryptionCredentialMatcher implements CredentialsMatcher {

    private EncryptionService encryptionService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String rawPassword = new String((char[]) token.getCredentials());
        String storedEncodedPassword = (String) info.getCredentials();

        return encryptionService.verifyPassword(new MasterPassword(rawPassword), storedEncodedPassword);
    }
}
