package port.in.registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;

/**
 * Defines the contract for registering new master users within the system.
 * Implementations of this interface provide the logic for creating a new
 * master account, including handling tasks such as password encoding
 * and persisting the user data.
 */
public interface RegistrationUseCase {

    /**
     * Registers a new master user with the provided login details and password.
     * Implementations should handle tasks such as validating input, encoding
     * the password for security, and persisting the new user account in the
     * system's data store.
     *
     * @param masterLogin The login details (e.g., username or email) for the new master account.
     * @param masterPassword The raw, plaintext password for the new master account.
     * Implementations are responsible for securely encoding this password before storage.
     *
     * @return The {@link Master} object representing the newly registered user account.
     */
    Master register(MasterLogin masterLogin, MasterPassword masterPassword);
}
