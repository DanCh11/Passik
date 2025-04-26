package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.encryption.Argon2Encoder;
import service.encryption.EncryptionService;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Fixtures.*;

public class EncryptionServiceTest {

    private EncryptionService encryptionService;
    String encodedMasterPassword;

    @BeforeEach
     void init() {
         Argon2Encoder argon2Encoder = new Argon2Encoder();
         encryptionService = new EncryptionService(argon2Encoder);
         encodedMasterPassword = encryptionService.encodePassword(basicMasterPassword);
     }

     @Test
     @DisplayName("Testing password encoding.")
     public void testEncodePassword() {
         assertNotNull(encodedMasterPassword);
         assertNotEquals(basicMasterPassword.rawPassword(), encodedMasterPassword);
     }

     @Test
     @DisplayName("Testing if raw password matches the encoded password.")
     public void testVerifyPassword() {
         assertTrue(encryptionService.verifyPassword(basicMasterPassword, encodedMasterPassword));
     }


     @Test
     @DisplayName("Testing if incorrect password will not pass verification.")
     public void testVerifyIncorrectMasterPassword() {
         assertFalse(encryptionService.verifyPassword(wrongMasterPassword, encodedMasterPassword));
     }

     @Test
     @DisplayName("Testing if same master password behave different in two encodings, due to salt.")
     public void testSameMasterPasswordEncodesDifferentlyDueToSalt() {
         String firstEncoder = encryptionService.encodePassword(basicMasterPassword);
         String secondEncoder = encryptionService.encodePassword(basicMasterPassword);

         assertNotEquals(firstEncoder, secondEncoder);
         assertTrue(encryptionService.verifyPassword(basicMasterPassword, firstEncoder));
         assertTrue(encryptionService.verifyPassword(basicMasterPassword, secondEncoder));
     }

    @Test
    @DisplayName("Testing if same credential password behave different in two encodings, due to salt.")
    public void testSameCredentialPasswordEncodesDifferentlyDueToSalt() {
        String firstEncoder = encryptionService.encodePassword(credentialPassword);
        String secondEncoder = encryptionService.encodePassword(credentialPassword);

        assertNotEquals(firstEncoder, secondEncoder);
        assertTrue(encryptionService.verifyPassword(credentialPassword, firstEncoder));
        assertTrue(encryptionService.verifyPassword(credentialPassword, secondEncoder));
    }
}
