package port.out.persistance;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;

import java.util.List;

/**
 * Defines the contract for accessing and managing {@link Master} entities
 * in the underlying data store (e.g., a database). Implementations of this
 * interface provide the necessary methods for performing CRUD-like operations
 * specifically for master user accounts.
 */
public interface MasterRepository {

    /**
     * Registers a new master user account in the data store.
     * This operation typically involves creating a new record for the master user,
     * associating their login details and securely stored password.
     *
     * @param masterLogin The login details for the new master user.
     * @param masterPassword The securely stored (hashed) password for the new master user.
     * @return The {@link Master} entity representing the newly registered user account,
     * potentially including a generated unique identifier.
     */
    Master register(MasterLogin masterLogin, MasterPassword masterPassword);

    /**
     * Retrieves a {@link Master} entity from the data store based on the provided
     * {@link MasterLogin}. This method is typically used during authentication
     * to find a user by their login identifier.
     *
     * @param masterLogin The login details of the master user to retrieve.
     * @return The {@link Master} entity matching the provided login, or {@code null}
     * if no such user is found.
     */
    Master getMasterByLogin(MasterLogin masterLogin);

    /**
     * Deletes an existing {@link Master} entity from the data store.
     * The implementation should identify the record to delete based on the
     * provided {@code master} object (typically by its unique identifier).
     *
     * @param master The {@link Master} entity to be removed.
     */
    void delete(Master master);

    /**
     * Retrieves all {@link Master} entities from the data store.
     * This method is used to fetch a complete list of all registered master users.
     *
     * @return A {@link List} containing all {@link Master} entities stored in
     * the data store. Returns an empty list if no master users are registered.
     */
    List<Master> findAll();
}
