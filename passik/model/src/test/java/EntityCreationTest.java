import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import de.daycu.passik.model.auth.PasswordNotCompleteException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntityCreationTest {

    @Test
    @SuppressWarnings("ConstantConditions")
    @DisplayName("Testing exception of null values")
    public void testEntitiesAreNull() {
        assertThrows(NullPointerException.class, () -> new MasterLogin(null));

        assertThrows(NullPointerException.class, () -> new MasterPassword(null));

        assertThrows(NullPointerException.class, () -> new MasterId(null));

        assertThrows(NullPointerException.class, () -> new Master(null,null, null));

        assertThrows(NullPointerException.class,
                () -> new Master(new MasterId(UUID.randomUUID()), new MasterLogin("login"), null));

    }

    @Test
    @SuppressWarnings("ConstantConditions")
    @DisplayName("Testing non emptiness of entities' values")
    public void testEntitiesHaveEmptyValues() {
        assertThrows(IllegalArgumentException.class, () -> new MasterLogin(""));
        assertThrows(IllegalArgumentException.class, () -> new MasterPassword(""));
    }

    @Test
    @DisplayName("Testing master password creation validity")
    public void testMasterPasswordCreationValidity() {
        assertThrows(PasswordNotCompleteException.class, () -> new MasterPassword("1234"));
        assertThrows(PasswordNotCompleteException.class, () -> new MasterPassword("masterpassword1!"));
        assertThrows(PasswordNotCompleteException.class, () -> new MasterPassword("masterPassword"));
    }
}
