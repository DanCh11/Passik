package port.in.auth;

import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import service.auth.AuthenticationResult;

public interface AuthenticationUseCase {
    AuthenticationResult authenticate(MasterLogin masterLogin, MasterPassword masterPassword);
}
