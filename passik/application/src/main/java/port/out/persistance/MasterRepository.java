package port.out.persistance;

import de.daycu.passik.model.security.Master;
import de.daycu.passik.model.security.MasterLogin;
import de.daycu.passik.model.security.MasterPassword;

public interface MasterRepository {
    boolean register(MasterLogin masterLogin, MasterPassword masterPassword);
    Master getMasterByLogin(MasterLogin masterLogin);
}
