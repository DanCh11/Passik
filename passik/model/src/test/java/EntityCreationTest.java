import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntityCreationTest {

    @Test
    @DisplayName("Testing exception of null values")
    public void testEntitiesAreNull() {
        NullPointerException MasterLoginNullException = assertThrows(NullPointerException.class,
                () -> new MasterLogin(null));

        NullPointerException MasterPasswordNullException = assertThrows(NullPointerException.class,
                () -> new MasterPassword(null));

        NullPointerException MasterNullLoginException = assertThrows(NullPointerException.class,
                () -> new Master(null, null));

        NullPointerException MasterNullPasswordException = assertThrows(NullPointerException.class,
                () -> new Master(new MasterLogin("login"), null));

        assertEquals("'value' must not be null", MasterLoginNullException.getMessage());
        assertEquals("'value' must not be null", MasterPasswordNullException.getMessage());
        assertEquals("'masterLogin' shall not be null", MasterNullLoginException.getMessage());
        assertEquals("'masterPassword' shall not be null", MasterNullPasswordException.getMessage());
    }

    @Test
    public void testEntitiesHaveEmptyValues() {
        IllegalArgumentException MasterLoginNullException = assertThrows(IllegalArgumentException.class,
                () -> new MasterLogin(""));

        IllegalArgumentException MasterPasswordNullException = assertThrows(IllegalArgumentException.class,
                () -> new MasterPassword(""));

        assertEquals("'value' must not be empty", MasterLoginNullException.getMessage());
        assertEquals("'value' must not be empty", MasterPasswordNullException.getMessage());
    }
}
