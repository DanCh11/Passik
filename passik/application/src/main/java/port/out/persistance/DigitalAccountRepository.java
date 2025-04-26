package port.out.persistance;

import java.util.List;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;

/**
 * Defines the contract for accessing and managing {@link DigitalAccount} entities
 * in the underlying data store (e.g., a database). Implementations of this
 * interface provide the necessary methods for performing CRUD (Create, Read,
 * Update, Delete) operations on digital account data.
 */
public interface DigitalAccountRepository {

    /**
     * Saves a new {@link DigitalAccount} entity to the data store.
     * If the entity has not been persisted before, this method should insert
     * a new record. If the entity already exists, the behavior might vary
     * depending on the implementation (e.g., it could throw an exception
     * or update the existing record).
     *
     * @param digitalAccount The {@link DigitalAccount} entity to be persisted.
     * @return The persisted {@link DigitalAccount} entity, potentially with
     * updated fields such as a generated ID.
     */
    DigitalAccount save(DigitalAccount digitalAccount);

    /**
     * Deletes an existing {@link DigitalAccount} entity from the data store.
     * The implementation should identify the record to delete based on the
     * provided {@code digitalAccount} object (typically by its unique identifier).
     *
     * @param digitalAccount The {@link DigitalAccount} entity to be removed.
     */
    void delete(DigitalAccount digitalAccount);

    /**
     * Updates an existing {@link DigitalAccount} entity in the data store.
     * The implementation should identify the record to update based on the
     * provided {@code updatedDigitalAccount} object (typically by its unique
     * identifier) and persist the changes to the data store.
     *
     * @param updatedDigitalAccount The {@link DigitalAccount} entity containing
     * the updated information.
     * @return The updated {@link DigitalAccount} entity after the changes have
     * been persisted.
     */
    DigitalAccount update(DigitalAccount updatedDigitalAccount);

    /**
     * Retrieves all {@link DigitalAccount} entities associated with a specific
     * {@link MasterId} from the data store.
     *
     * @param masterId The unique identifier of the master user whose digital
     * accounts are being requested.
     * @return A {@link List} of {@link DigitalAccount} objects belonging to the
     * specified master. Returns an empty list if no accounts are found.
     */
    List<DigitalAccount> findAllByMasterId(MasterId masterId);

    /**
     * Retrieves a specific {@link DigitalAccount} entity from the data store
     * based on the provided {@link MasterId} and {@link DigitalServiceName}.
     * This method is used to find a unique digital account for a given user
     * and service.
     *
     * @param masterId The unique identifier of the master user.
     * @param digitalServiceName The unique name of the digital service associated
     * with the account.
     * @return The {@link DigitalAccount} entity matching the provided criteria,
     * or {@code null} if no such account is found.
     */
    DigitalAccount findByDigitalServiceName(MasterId masterId, DigitalServiceName digitalServiceName);
}
