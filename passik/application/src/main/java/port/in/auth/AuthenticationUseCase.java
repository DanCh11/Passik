package port.in.auth;

import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import org.apache.shiro.authc.AuthenticationException;
import service.auth.AuthenticationResult;

/**
 * Defines the contract for authenticating master users.
 * Implementations of this interface provide the logic for verifying a user's
 * credentials (login and password) against the system's stored user data.
 */
public interface AuthenticationUseCase {

    /**
     * Attempts to authenticate a master user with the provided login and password.
     * @param masterLogin The login details of the master user attempting to authenticate.
     * @param masterPassword The raw password provided by the master user for authentication.
     *
     * @return An {@link AuthenticationResult} object indicating the outcome of the authentication attempt.
     * This result typically includes a boolean flag for success or failure and an optional message.
     *
     * @throws AuthenticationException If the authentication process encounters an error. Specific
     * implementations may throw more specific subtypes like
     * {@link org.apache.shiro.authc.IncorrectCredentialsException}
     * or {@link org.apache.shiro.authc.UnknownAccountException}.
     */
    AuthenticationResult authenticate(MasterLogin masterLogin, MasterPassword masterPassword);
}
