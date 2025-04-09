package port.in.vault;

import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;
import service.vault.DigitalAccountDeletionResult;

public interface DigitalAccountUseCase {
    DigitalAccount save(DigitalAccount digitalAccount);
    DigitalAccount update(DigitalServiceName digitalServiceName, DigitalAccount digitalAccount);
    DigitalAccountDeletionResult delete(DigitalAccount digitalAccount);

}
