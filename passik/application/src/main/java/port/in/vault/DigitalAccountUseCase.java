package port.in.vault;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;
import service.vault.DigitalAccountDeletionResult;
import service.vault.exception.DigitalAccountNotFoundException;
import service.vault.exception.DuplicateAccountException;

import java.util.List;

/**
 * Defines the contract for managing digital accounts associated with a master user.
 * Implementations of this interface provide the business logic for performing
 * CRUD (Create, Read, Update, Delete) operations on digital account entities,
 * often including tasks like data validation and ensuring data integrity.
 */
public interface DigitalAccountUseCase {

    /**
     * Saves a new digital account.
     * Implementations should handle the creation and persistence of a new
     * digital account record in the system's data store.
     *
     * @param digitalAccount The digital account object to be saved.
     * @return The saved {@link DigitalAccount} object, potentially with an assigned ID
     * or other updates made during the save operation.
     *
     * @throws DuplicateAccountException If an account with the same identifying
     * information (e.g., master ID and digital service name) already exists.
     */
    DigitalAccount save(DigitalAccount digitalAccount);

    /**
     * Retrieves all digital accounts associated with a specific master user ID.
     * Implementations should query the system's data store to find all digital
     * accounts that belong to the given master.
     *
     * @param masterId The unique identifier of the master user whose digital
     * accounts are being requested.
     * @return A {@link List} of {@link DigitalAccount} objects associated with the
     * specified master ID. Returns an empty list if no accounts are found.
     */
    List<DigitalAccount> findAllByMasterId(MasterId masterId);

    /**
     * Updates an existing digital account.
     * Implementations should handle the modification and persistence of an existing
     * digital account record in the system's data store, identified by its
     * digital service name for a given master.
     *
     * @param digitalServiceName The unique name of the digital service associated
     * with the account to be updated. This, in conjunction with the master ID
     * (typically found within the {@code digitalAccount} parameter), identifies
     * the account to update.
     * @param digitalAccount The {@link DigitalAccount} object containing the updated
     * information.
     * @return The updated {@link DigitalAccount} object after the changes have been
     * persisted.
     *
     * @throws DigitalAccountNotFoundException If no digital account is found matching
     * the provided criteria (master ID and digital service name).
     */
    DigitalAccount update(DigitalServiceName digitalServiceName, DigitalAccount digitalAccount);

    /**
     * Deletes a digital account.
     * Implementations should handle the removal of a digital account record from
     * the system's data store, identified by its master ID and digital service name.
     *
     * @param digitalAccount The {@link DigitalAccount} object to be deleted. The
     * master ID and digital service name within this object are typically used
     * to identify the account to remove.
     * @return A {@link DigitalAccountDeletionResult} object indicating the outcome
     * of the deletion operation (e.g., success status and a message).
     *
     * @throws DigitalAccountNotFoundException If no digital account is found matching
     * the identifying information within the provided {@code digitalAccount}.
     */
    DigitalAccountDeletionResult delete(DigitalAccount digitalAccount);
}
