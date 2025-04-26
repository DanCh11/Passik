package service.vault;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.vault.CredentialPassword;
import de.daycu.passik.model.vault.Credentials;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;
import lombok.AllArgsConstructor;
import port.in.vault.DigitalAccountUseCase;
import port.out.persistance.DigitalAccountRepository;
import service.encryption.EncryptionService;
import service.vault.exception.*;

import java.util.List;

/**
 * Service responsible for managing digital accounts for users.
 * Handles operations such as saving, retrieving, updating, and deleting accounts,
 * ensuring password encryption for sensitive credentials.
 */
@AllArgsConstructor
public class DigitalAccountService implements DigitalAccountUseCase {
    
    private final DigitalAccountRepository digitalAccountRepository;
    private final EncryptionService encryptionService;

    /**
     * Saves a new digital account.
     * Ensures that an account with the same master ID and service name does not already exist.
     * Encrypts the account's password before saving it to the repository.
     * @param digitalAccount The digital account to save.
     * @return The saved digital account with its (potentially) assigned ID.
     * @throws DuplicateAccountException If an account with the same master ID and service name already exists.
     */
    @Override
    public DigitalAccount save(DigitalAccount digitalAccount) {
        ensureDigitalAccountDoesNotExist(digitalAccount.masterId(), digitalAccount.digitalServiceName());
        DigitalAccount encryptedDigitalAccount = encryptPassword(digitalAccount);

        return digitalAccountRepository.save(encryptedDigitalAccount);
    }

    /**
     * Retrieves all digital accounts associated with a specific master ID.
     * @param masterId The ID of the master user.
     * @return A list of digital accounts belonging to the specified master.
     */
    @Override
    public List<DigitalAccount> findAllByMasterId(MasterId masterId) {
        return digitalAccountRepository.findAllByMasterId(masterId);
    }

    /**
     * Updates an existing digital account's credentials.
     * Finds the account by its service name for a given master and updates its login and password.
     * The new password is encrypted before being saved.
     * @param digitalServiceName The service name of the account to update.
     * @param updatedAccount The digital account containing the updated credentials.
     * @return The updated digital account.
     * @throws DigitalAccountNotFoundException If no digital account is found for the given service name and master ID.
     */
    @Override
    public DigitalAccount update(DigitalServiceName digitalServiceName, DigitalAccount updatedAccount) {
        DigitalAccount existingDigitalAccount = findExistingDigitalAccount(
                updatedAccount.masterId(), digitalServiceName);
        DigitalAccount encryptedAccount = encryptPassword(existingDigitalAccount);

        return digitalAccountRepository.save(encryptedAccount);
    }

    /**
     * Deletes a digital account.
     * Finds the account by its master ID and service name and removes it from the repository.
     * @param digitalAccount The digital account to delete.
     * @return A {@link DigitalAccountDeletionResult} indicating the success of the deletion.
     * @throws DigitalAccountNotFoundException If no digital account is found for the given master ID and service name.
     */
    @Override
    public DigitalAccountDeletionResult delete(DigitalAccount digitalAccount) {
        DigitalAccount existingDigitalAccount = findExistingDigitalAccount(
                digitalAccount.masterId(), digitalAccount.digitalServiceName());

        digitalAccountRepository.delete(existingDigitalAccount);

        return new DigitalAccountDeletionResult(true,
                "Digital account deleted successfully for service: %s".formatted(digitalAccount.digitalServiceName()));
    }

    /**
     * Helper method to check if a digital account already exists for a given master and service.
     * @param masterId The ID of the master user.
     * @param digitalServiceName The name of the digital service.
     * @throws DuplicateAccountException If an account already exists.
     */
    private void ensureDigitalAccountDoesNotExist(MasterId masterId, DigitalServiceName digitalServiceName) {
        DigitalAccount existingDigitalAccount = digitalAccountRepository
                .findByDigitalServiceName(masterId, digitalServiceName);
        if (existingDigitalAccount != null)
            throw new DuplicateAccountException("Account already exists for service: %s".formatted(digitalServiceName));
    }

    /**
     * Helper method to find an existing digital account by master ID and service name.
     * @param masterId The ID of the master user.
     * @param digitalServiceName The name of the digital service.
     * @return The found digital account.
     * @throws DigitalAccountNotFoundException If no account is found.
     */
    private DigitalAccount findExistingDigitalAccount(MasterId masterId, DigitalServiceName digitalServiceName) {
        DigitalAccount existingDigitalAccount = digitalAccountRepository
                .findByDigitalServiceName(masterId, digitalServiceName);

        if (existingDigitalAccount == null)
            throw new DigitalAccountNotFoundException("Digital account not found for service: %s"
                    .formatted(digitalServiceName));

        return existingDigitalAccount;
    }

    /**
     * Helper method to encrypt the password of a digital account.
     * Creates a new {@link DigitalAccount} with the encrypted password.
     * @param digitalAccount The digital account whose password needs to be encrypted.
     * @return A new {@link DigitalAccount} instance with the encrypted password.
     */
    private DigitalAccount encryptPassword(DigitalAccount digitalAccount) {
        String encodedPassword = encryptionService.encodePassword(
                digitalAccount.credentials().credentialPassword());
        CredentialPassword encodedCredentialPassword = new CredentialPassword(encodedPassword);
        return digitalAccount.toBuilder()
                .credentials(new Credentials(digitalAccount.credentials().credentialLogin(), encodedCredentialPassword))
                .build();
    }

}
