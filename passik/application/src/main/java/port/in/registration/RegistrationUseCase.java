package port.in.registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;

public interface RegistrationUseCase {
    Master register(MasterLogin masterLogin, MasterPassword masterPassword);
}
