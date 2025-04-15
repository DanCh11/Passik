package port.out.persistance;

import java.util.List;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;

public interface DigitalAccountRepository {
    DigitalAccount save(DigitalAccount digitalAccount);
    void delete(DigitalAccount digitalAccount);
    DigitalAccount update(DigitalAccount updatedDigitalAccount);
    List<DigitalAccount> findAllByMasterId(MasterId masterId);
    DigitalAccount findByDigitalServiceName(MasterId masterId, DigitalServiceName digitalServiceName);
}
