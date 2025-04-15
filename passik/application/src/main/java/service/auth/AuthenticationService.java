package service.auth;

import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import port.in.auth.AuthenticationUseCase;

/**
 *  Performs authentication by instantiating the users using UserSecurityManager.
 *
 *  @return successful authentication if the user is found. Else throws an exception.
 */
public class AuthenticationService implements AuthenticationUseCase {

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
}
