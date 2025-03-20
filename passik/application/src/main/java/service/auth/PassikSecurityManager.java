package service.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;

import java.util.Objects;

/**
 * Main record of configuring a security manager to interact with realms.
 * @param realm
 */
public record PassikSecurityManager(Realm realm) {

    public PassikSecurityManager {
        Objects.requireNonNull(realm, "'realm shall not be null'");
    }

    public Subject configure() {
        SecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);

        return SecurityUtils.getSubject();
    }
}
