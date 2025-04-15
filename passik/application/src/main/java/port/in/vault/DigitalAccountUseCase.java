package port.in.vault;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;
import service.vault.DigitalAccountDeletionResult;

import java.util.List;

public interface DigitalAccountUseCase {
    DigitalAccount save(DigitalAccount digitalAccount);
    List<DigitalAccount> findAllByMasterId(MasterId masterId);
    DigitalAccount update(DigitalServiceName digitalServiceName, DigitalAccount digitalAccount);
    DigitalAccountDeletionResult delete(DigitalAccount digitalAccount);

}
