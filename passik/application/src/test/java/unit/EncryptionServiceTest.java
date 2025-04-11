package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.encryption.EncryptionService;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Fixtures.basicMasterPassword;
import static utils.Fixtures.wrongMasterPassword;

public class EncryptionServiceTest {

    private EncryptionService encryptionService;

    @BeforeEach
    void init() {
        encryptionService = new EncryptionService();
    }

    @Test
    @DisplayName("Testing password encoding.")
    public void testEncodePassword() {
        String encoder = encryptionService.encodePassword(basicMasterPassword);

        assertNotNull(encoder);
        assertNotEquals(encoder, basicMasterPassword.rawPassword());
    }

    @Test
    @DisplayName("Testing if raw password matches the encoded password.")
    public void testVerifyPassword() {
        String encoder = encryptionService.encodePassword(basicMasterPassword);

        assertTrue(encryptionService.verifyPassword(basicMasterPassword, encoder));
    }

    @Test
    @DisplayName("Testing if incorrect password will not pass verification.")
    public void testVerifyIncorrectPassword() {
        String encoder = encryptionService.encodePassword(basicMasterPassword);

        assertFalse(encryptionService.verifyPassword(wrongMasterPassword, encoder));
    }

    @Test
    @DisplayName("Testing if same password behave different in two encodings, due to salt.")
    public void testSamePasswordEncodesDifferentlyDueToSalt() {
        String firstEncoder = encryptionService.encodePassword(basicMasterPassword);
        String secondEncoder = encryptionService.encodePassword(basicMasterPassword);

        assertNotEquals(firstEncoder, secondEncoder);
        assertTrue(encryptionService.verifyPassword(basicMasterPassword, firstEncoder));
        assertTrue(encryptionService.verifyPassword(basicMasterPassword, secondEncoder));
    }
}
