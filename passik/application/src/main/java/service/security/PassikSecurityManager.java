package service.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;

import java.util.Objects;

public record UserSecurityManager(Realm realm) {

    public UserSecurityManager {
        Objects.requireNonNull(realm, "'realm shall not be null'");
    }

    public Subject configure() {
        SecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);

        return SecurityUtils.getSubject();
    }
}
