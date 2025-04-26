package service.auth;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import lombok.NonNull;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;

/**
 * Shiro Realm responsible for authenticating master users.
 * This Realm interacts with the {@link MasterRepository} to retrieve user data
 * and uses a custom {@link EncryptionCredentialMatcher} to verify passwords.
 */
public final class MasterRealm extends AuthenticatingRealm {

    private final MasterRepository masterRepository;

    /**
     * Constructs a new {@code MasterRealm}.
     * Initializes the Realm with the necessary {@link MasterRepository} for
     * accessing user data and configures a custom {@link EncryptionCredentialMatcher}
     * to handle password verification using the application's encryption service.
     *
     * @param masterRepository The repository for accessing master user data. Must not be null.
     * @param encryptionService The service responsible for password encoding and verification. Must not be null.
     */
    public MasterRealm(@NonNull MasterRepository masterRepository, @NonNull EncryptionService encryptionService) {
        this.masterRepository = masterRepository;
        setCredentialsMatcher(new EncryptionCredentialMatcher(encryptionService));
    }

    /**
     * Retrieves authentication information for the user based on the provided authentication token.
     * This method is called by Shiro during the authentication process. It looks up the user
     * by their login principal and, if found, creates an {@link AuthenticationInfo} object
     * containing the user's credentials (the hashed password) and other relevant information.
     *
     * @param token The authentication token provided by the user, containing their login principal (username).
     * @return An {@link AuthenticationInfo} object containing the user's authentication details.
     * @throws AuthenticationException If the authentication fails, such as if the account is not found.
     * Specifically throws {@link UnknownAccountException} if no user is found with the given login.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MasterLogin masterLogin = new MasterLogin((String) token.getPrincipal());
        Master master = masterRepository.getMasterByLogin(masterLogin);
        if (master == null) throw new UnknownAccountException("Account is not registered.");

        return new SimpleAuthenticationInfo(
                master.masterLogin().value(),
                master.masterPassword().rawPassword(),
                getName());
    }
}
