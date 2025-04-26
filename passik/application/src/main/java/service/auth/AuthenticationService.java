package service.auth;

import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import lombok.NonNull;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import port.in.auth.AuthenticationUseCase;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;

/**
 * Service responsible for handling user authentication.
 * Leverages the Apache Shiro security framework to authenticate master users
 * against the data stored in the {@link MasterRepository}, using the
 * {@link EncryptionService} for password verification indirectly through Shiro.
 */
public class AuthenticationService implements AuthenticationUseCase {

    private final MasterRepository masterRepository;
    private final EncryptionService encryptionService;

    /**
     * Constructs a new {@code AuthenticationService}.
     * Initializes the service with the necessary {@link MasterRepository} for
     * accessing user data and the {@link EncryptionService} for password
     * verification. It also initializes the Shiro security manager during
     * construction.
     *
     * @param masterRepository The repository for accessing master user data. Must not be null.
     * @param encryptionService The service responsible for password encoding and verification. Must not be null.
     */
    public AuthenticationService(
            @NonNull MasterRepository masterRepository,
            @NonNull EncryptionService encryptionService) {

        this.masterRepository = masterRepository;
        this.encryptionService = encryptionService;
        initializeShiro();
    }

    /**
     * Authenticates a master user based on the provided login and password.
     * Uses Apache Shiro to perform the authentication process. It creates an
     * authentication token with the user's credentials and attempts to log them in.
     * Exceptions are caught and re-thrown as specific {@link AuthenticationException}
     * subtypes to provide more context about the authentication failure.
     *
     * @param masterLogin The login details of the master user.
     * @param masterPassword The raw password provided by the master user for authentication.
     * @return An {@link AuthenticationResult} indicating the success or failure of the authentication.
     *
     * @throws IncorrectCredentialsException If the provided password does not match the stored password for the user.
     * @throws UnknownAccountException If no account is found for the given login.
     * @throws AuthenticationException If a general error occurs during the authentication process.
     */
    public AuthenticationResult authenticate(MasterLogin masterLogin, MasterPassword masterPassword) {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                masterLogin.value(), masterPassword.rawPassword());

        try {
            user.login(usernamePasswordToken);
            return new AuthenticationResult(true, "Login was performed successfully.");
        } catch (IncorrectCredentialsException ice) {
            throw new IncorrectCredentialsException(
                    "Invalid credentials provided. Please check your username and password.", ice);
        } catch (UnknownAccountException uae) {
            throw new UnknownAccountException("Account is not registered", uae);
        } catch (AuthenticationException ae) {
            throw new AuthenticationException("Authentication failed due to error: ", ae);
        }
    }

    /**
     * Initializes the Apache Shiro security framework.
     * This method is called once during the construction of the {@code AuthenticationService}.
     * It creates a {@link MasterRealm}, which is responsible for authenticating master users,
     * configures it to use the application's {@link MasterRepository} and {@link EncryptionService}
     * (via the {@link EncryptionCredentialMatcher} set in the Realm), and sets up a
     * {@link DefaultSecurityManager} with this Realm. Finally, it makes this security manager
     * the globally accessible security manager for Shiro. The `synchronized` keyword ensures
     * that this initialization happens only once, even in a multi-threaded environment.
     */
    private synchronized void initializeShiro() {
        MasterRealm masterRealm = new MasterRealm(masterRepository, encryptionService);
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(masterRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

}
