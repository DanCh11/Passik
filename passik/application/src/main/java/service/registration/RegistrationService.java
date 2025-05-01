package service.registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import port.in.registration.RegistrationUseCase;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;

/**
 * Service responsible for registering new master accounts.
 * Handles the encoding of the master password before persisting the account.
 */
@AllArgsConstructor
public class RegistrationService implements RegistrationUseCase {

    @NonNull private final MasterRepository masterRepository;
    @NonNull private final EncryptionService encryptionService;

    /**
     * Registers a new master account.
     * Encodes the provided master password before saving it via the repository.
     * @param masterLogin The login details for the new master account.
     * @param masterPassword The raw password for the new master account. This will be encoded.
     * @return The newly registered Master account.
     */
    @Override
    public Master register(MasterLogin masterLogin, MasterPassword masterPassword) {
        String encodedPassword = encryptionService.encodePassword(masterPassword);
        MasterPassword encodedMasterPassword = new MasterPassword(encodedPassword);

        return masterRepository.register(masterLogin, encodedMasterPassword);
    }
}
