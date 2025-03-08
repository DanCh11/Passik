package service.security;

import de.daycu.passik.model.security.MasterLogin;
import de.daycu.passik.model.security.MasterPassword;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *  Performs authentication by instantiating the users using UserSecurityManager
 *  with injected UserRealm class.
 *
 *  @return successful authentication if the user is found. Else throws an exception.
 */
@AllArgsConstructor
public class AuthenticationService {

    private MasterLogin masterLogin;
    private MasterPassword masterPassword;
    private UserRealm userRealm;

    public AuthenticationResult authenticate() {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                masterLogin.toString(), masterPassword.toString());
        Subject user = new PassikSecurityManager(userRealm).configure();

        try {
            user.login(usernamePasswordToken);
            return new AuthenticationResult(true, "Login was performed successfully.");
        } catch (UnknownAccountException uae) {
            return new AuthenticationResult(false, "Account is not registered.");
        } catch (IncorrectCredentialsException ice) {
            return new AuthenticationResult(false,
                    "Invalid credentials provided. Please check your username and password.");
        } catch (AuthenticationException ae) {
            return new AuthenticationResult(false, "Unknown error.");
        }
    }
}
