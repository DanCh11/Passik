package service.auth;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;

/**
 * Creates a realm to interact with users' authentication data.
 */
public class MasterRealm extends AuthenticatingRealm {

    private final MasterRepository masterRepository;

    public MasterRealm(MasterRepository masterRepository, EncryptionService encryptionService) {
        this.masterRepository = masterRepository;
        setCredentialsMatcher(new EncryptionCredentialMatcher(encryptionService));
    }

    /**
     * Retrieves authentication information of the user.
     * @param token the authentication token containing the user's principal and credentials.
     *
     * @return authentication info
     * @throws AuthenticationException if the authentication failed.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MasterLogin masterLogin = new MasterLogin((String) token.getPrincipal());
        Master master = masterRepository.getMasterByLogin(masterLogin);
        if (master == null) throw new UnknownAccountException("Account is not registered.");

        return new SimpleAuthenticationInfo(
                master.masterLogin().value(),
                master.masterPassword().value(),
                getName());
    }
}
