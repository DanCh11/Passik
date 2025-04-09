package vault;

import de.daycu.passik.model.vault.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import port.out.persistance.DigitalAccountRepository;
import service.encryption.EncryptionService;
import service.vault.DigitalAccountDeletionResult;
import service.vault.DigitalAccountService;
import service.vault.exception.DigitalAccountNotFoundException;
import service.vault.exception.DuplicateAccountException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DigitalAccountServiceTest {

    @Mock private DigitalAccountRepository digitalAccountRepository;
    @Mock private EncryptionService encryptionService;
    private DigitalAccountService credentialsService;
    private DigitalAccount digitalAccount;
    private DigitalServiceName digitalServiceName;
    private CredentialLogin credentialLogin;
    private CredentialPassword credentialPassword;

    @BeforeEach
    void setup() {
        credentialLogin = new CredentialLogin("login");
        credentialPassword = new CredentialPassword("password");
        Credentials credentials = new Credentials(credentialLogin, credentialPassword);
        digitalServiceName = new DigitalServiceName("digital service");
        digitalAccount = new DigitalAccount(digitalServiceName, credentials);
        credentialsService = new DigitalAccountService(digitalAccountRepository, encryptionService);
    }

    @Test
    @DisplayName("Testing successful digital account creation")
    public void testSuccessfulDigitalAccountCreation() {
        final String encryptedPassword = "encryptedPassword";
        CredentialPassword encryptedCredentialPassword = new CredentialPassword(encryptedPassword);
        Credentials encryptedCredentials = new Credentials(credentialLogin, encryptedCredentialPassword);
        DigitalAccount digitalAccountWithEncryptedPassword = new DigitalAccount(digitalServiceName, encryptedCredentials);

        lenient().when(encryptionService.encodeCredentialPassword(credentialPassword)).thenReturn(encryptedCredentialPassword);
        lenient().when(digitalAccountRepository.save(any(DigitalAccount.class))).thenReturn(digitalAccountWithEncryptedPassword);

        DigitalAccount createdDigitalAccount = credentialsService.save(digitalAccount);
        verify(digitalAccountRepository).save(argThat(account ->
                account.credentials().credentialPassword().rawPassword().equals(encryptedPassword)));

        assertNotNull(createdDigitalAccount);
        assertEquals(digitalAccountWithEncryptedPassword, createdDigitalAccount);
    }

    @Test
    @DisplayName("Testing successful digital account update")
    public void testSuccessfulDigitalAccountUpdate() {
        final String encryptedPassword = "encryptedPassword";
        CredentialLogin updatedCredentialLogin = new CredentialLogin("updatedLogin");
        CredentialPassword encryptedCredentialPassword = new CredentialPassword(encryptedPassword);
        Credentials updateCredentials = new Credentials(updatedCredentialLogin, encryptedCredentialPassword);
        DigitalAccount updatedDigitalAccount = new DigitalAccount(digitalServiceName, updateCredentials);

        lenient().when(digitalAccountRepository.findByDigitalServiceName(digitalServiceName)).thenReturn(digitalAccount);
        lenient().when(encryptionService.encodeCredentialPassword(any(CredentialPassword.class))).thenReturn(encryptedCredentialPassword);
        lenient().when(digitalAccountRepository.save(any(DigitalAccount.class))).thenReturn(updatedDigitalAccount);

        DigitalAccount update = credentialsService.update(digitalServiceName, updatedDigitalAccount);

        assertNotNull(update);
        assertEquals(updatedDigitalAccount, update);
    }

    @Test
    @DisplayName("Testing successful digital account deletion")
    public void testSuccessfulDigitalAccountDeletion() {
        lenient().when(digitalAccountRepository.findByDigitalServiceName(digitalServiceName)).thenReturn(digitalAccount);

        credentialsService = new DigitalAccountService(digitalAccountRepository, encryptionService);
        DigitalAccountDeletionResult deletedDigitalAccount = credentialsService.delete(digitalAccount);

        assertTrue(deletedDigitalAccount.isSuccessful());
        assertEquals(
                "Digital account deleted successfully for service: DigitalServiceName[name=digital service]",
                deletedDigitalAccount.message());
    }

    @Test
    @DisplayName("Testing exception of duplication saving")
    public void testSaveAlreadyExistingAccountException() {
        lenient().when(digitalAccountRepository.findByDigitalServiceName(digitalServiceName)).thenReturn(digitalAccount);
        assertThrows(DuplicateAccountException.class, () -> credentialsService.save(digitalAccount));
    }

    @Test
    @DisplayName("Testing exception of enabledness finding the digital account")
    public void testNotFoundDigitalAccountException() {
        lenient().when(digitalAccountRepository.findByDigitalServiceName(digitalServiceName)).thenReturn(null);
        assertThrows(DigitalAccountNotFoundException.class, () -> credentialsService.update(digitalServiceName, digitalAccount));
    }

}
