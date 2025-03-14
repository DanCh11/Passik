package service.security;

import de.daycu.passik.model.security.MasterLogin;
import de.daycu.passik.model.security.MasterPassword;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import port.in.security.AuthenticationUseCase;

/**
 *  Performs authentication by instantiating the users using UserSecurityManager
 *  with injected UserRealm class.
 *
 *  @return successful authentication if the user is found. Else throws an exception.
 */
@AllArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

    private MasterRealm masterRealm;

    public AuthenticationResult authenticate(MasterLogin masterLogin, MasterPassword masterPassword) {

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                masterLogin.value(), masterPassword.value());
        Subject user = new PassikSecurityManager(masterRealm).configure();

        try {
            user.login(usernamePasswordToken);
            return new AuthenticationResult(true, "Login was performed successfully.");
        } catch (IncorrectCredentialsException ice) {
            return new AuthenticationResult(false,
                    "Invalid credentials provided. Please check your username and password.");
        }
    }
}
