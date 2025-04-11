package unit;

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
import static utils.Fixtures.*;

@ExtendWith(MockitoExtension.class)
public class DigitalAccountServiceTest {

    @Mock private DigitalAccountRepository digitalAccountRepository;
    @Mock private EncryptionService encryptionService;
    private DigitalAccountService credentialsService;

    @BeforeEach
    void setup() {
        credentialsService = new DigitalAccountService(digitalAccountRepository, encryptionService);
    }

    @Test
    @DisplayName("Testing successful digital account creation")
    public void testSuccessfulDigitalAccountCreation() {
        lenient().when(encryptionService.encodeCredentialPassword(credentialPassword)).thenReturn(encryptedCredentialPassword);
        lenient().when(digitalAccountRepository.save(any(DigitalAccount.class))).thenReturn(digitalAccountWithEncryptedPassword);

        DigitalAccount createdDigitalAccount = credentialsService.save(digitalAccount);
        verify(digitalAccountRepository).save(argThat(account ->
                account.credentials().credentialPassword().rawPassword().equals(encryptedCredentialPassword.rawPassword())));

        assertNotNull(createdDigitalAccount);
        assertEquals(digitalAccountWithEncryptedPassword, createdDigitalAccount);
    }

    @Test
    @DisplayName("Testing successful digital account update")
    public void testSuccessfulDigitalAccountUpdate() {
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
