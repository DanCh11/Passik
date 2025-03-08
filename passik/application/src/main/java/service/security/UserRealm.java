package service.security;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

import java.util.HashMap;

/**
 * Creates a realm to interact with users' authentication data.
 */
@AllArgsConstructor
public class UserRealm extends AuthenticatingRealm {

    private HashMap<String, String> users;

    /**
     * Retrieves authentication information of the user.
     * @param token the authentication token containing the user's principal and credentials.
     *
     * @return authentication info
     * @throws AuthenticationException if the authentication failed.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        if (!users.containsKey(username)) {
            System.out.println("There is no such user");
        }

        return new SimpleAuthenticationInfo(username, users.get(username), getName());
    }
}
