package port.in.security;

import de.daycu.passik.model.security.MasterLogin;
import de.daycu.passik.model.security.MasterPassword;
import service.security.AuthenticationResult;

public interface AuthenticationUseCase {
    AuthenticationResult authenticate(MasterLogin masterLogin, MasterPassword masterPassword);
}
