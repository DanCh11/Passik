package port.out.persistance;

import java.util.List;

import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;

public interface DigitalAccountRepository {
    DigitalAccount save(DigitalAccount digitalAccount);
    void delete(DigitalAccount digitalAccount);
    DigitalAccount update(DigitalServiceName digitalServiceName, DigitalAccount updatedDigitalAccount);
    List<DigitalAccount> findAll();
    DigitalAccount findByDigitalServiceName(DigitalServiceName digitalServiceName);
}
