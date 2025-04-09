package encryption;

import de.daycu.passik.model.auth.MasterPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.encryption.EncryptionService;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionServiceTest {

    private EncryptionService encryptionService;
    private MasterPassword masterPassword;

    @BeforeEach
    void init() {
        encryptionService = new EncryptionService();
    }

    @Test
    @DisplayName("Testing password encoding.")
    public void testEncodePassword() {
        masterPassword = new MasterPassword("masterPassword");
        String encoder = encryptionService.encodePassword(masterPassword);

        assertNotNull(encoder);
        assertNotEquals(encoder, masterPassword.rawPassword());
    }

    @Test
    @DisplayName("Testing if raw password matches the encoded password.")
    public void testVerifyPassword() {
        masterPassword = new MasterPassword("masterPassword");
        String encoder = encryptionService.encodePassword(masterPassword);

        assertTrue(encryptionService.verifyPassword(masterPassword, encoder));
    }

    @Test
    @DisplayName("Testing if incorrect password will not pass verification.")
    public void testVerifyIncorrectPassword() {
        masterPassword = new MasterPassword("masterPassword");
        String encoder = encryptionService.encodePassword(masterPassword);
        masterPassword = new MasterPassword("wrongMasterPassword");

        assertFalse(encryptionService.verifyPassword(masterPassword, encoder));
    }

    @Test
    @DisplayName("Testing if same password behave different in two encodings, due to salt.")
    public void testSamePasswordEncodesDifferentlyDueToSalt() {
        masterPassword = new MasterPassword("masterPassword");
        String firstEncoder = encryptionService.encodePassword(masterPassword);
        String secondEncoder = encryptionService.encodePassword(masterPassword);

        assertNotEquals(firstEncoder, secondEncoder);
        assertTrue(encryptionService.verifyPassword(masterPassword, firstEncoder));
        assertTrue(encryptionService.verifyPassword(masterPassword, secondEncoder));
    }
}
