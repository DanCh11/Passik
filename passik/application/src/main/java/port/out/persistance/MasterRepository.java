package port.out.persistance;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;

import java.util.List;

public interface MasterRepository {
    Master register(MasterLogin masterLogin, MasterPassword masterPassword);
    Master getMasterByLogin(MasterLogin masterLogin);
    void delete(Master master);
    List<Master> findAll();
}
