package service.vault;

import de.daycu.passik.model.vault.CredentialPassword;
import de.daycu.passik.model.vault.Credentials;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;
import lombok.AllArgsConstructor;
import port.in.vault.DigitalAccountUseCase;
import port.out.persistance.DigitalAccountRepository;
import service.encryption.EncryptionService;
import service.vault.exception.*;

@AllArgsConstructor
public class DigitalAccountService implements DigitalAccountUseCase {
    
    private final DigitalAccountRepository digitalAccountRepository;
    private final EncryptionService encryptionService;

    @Override
    public DigitalAccount save(DigitalAccount digitalAccount) {
        if (digitalAccountRepository.findByDigitalServiceName(digitalAccount.digitalServiceName()) != null)
            throw new DuplicateAccountException(
                    "Account already exists for service: %s".formatted(digitalAccount.digitalServiceName()));

        DigitalAccount encryptedDigitalAccount = createEncryptedDigitalAccount(digitalAccount);

        return digitalAccountRepository.save(encryptedDigitalAccount);
    }

    @Override
    public DigitalAccount update(DigitalServiceName digitalServiceName, DigitalAccount updatedAccount) {
        DigitalAccount existingDigitalAccount = digitalAccountRepository.findByDigitalServiceName(digitalServiceName);

        if (existingDigitalAccount == null)
            throw new DigitalAccountNotFoundException(
                    "Digital account not found for service: %s".formatted(digitalServiceName));

        CredentialPassword encryptedCredentialPassword = encryptionService
                .encodeCredentialPassword(updatedAccount.credentials().credentialPassword());

        DigitalAccount updatedDigitalAccountCredentials = existingDigitalAccount.toBuilder()
                .credentials(new Credentials(updatedAccount.credentials().credentialLogin(), encryptedCredentialPassword))
                .build();

        return digitalAccountRepository.save(updatedDigitalAccountCredentials);
    }

    @Override
    public DigitalAccountDeletionResult delete(DigitalAccount digitalAccount) {
        DigitalAccount existingDigitalAccount = digitalAccountRepository
                .findByDigitalServiceName(digitalAccount.digitalServiceName());

        if (existingDigitalAccount == null)
            throw new DigitalAccountNotFoundException(
                    "Digital account not found for service: %s".formatted(digitalAccount.digitalServiceName()));

        digitalAccountRepository.delete(existingDigitalAccount);

        return new DigitalAccountDeletionResult(true,
                "Digital account deleted successfully for service: %s".formatted(digitalAccount.digitalServiceName()));
    }

    private DigitalAccount createEncryptedDigitalAccount(DigitalAccount digitalAccount) {
        CredentialPassword encodedCredentialPassword = encryptionService.encodeCredentialPassword(
                digitalAccount.credentials().credentialPassword());

        return digitalAccount.toBuilder()
                .credentials(new Credentials(digitalAccount.credentials().credentialLogin(), encodedCredentialPassword))
                .build();
    }

}
