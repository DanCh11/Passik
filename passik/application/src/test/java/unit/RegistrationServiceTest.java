package unit;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;
import service.registration.PasswordNotCompleteException;
import service.registration.RegistrationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static utils.Fixtures.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock private MasterRepository masterRepository;
    @Mock private EncryptionService encryptionService;
    private RegistrationService registrationService;
    private Master master;

    @Test
    @DisplayName("Testing successful registration")
    public void testSuccessfulRegistration() {
        master = new Master(basicMasterLogin, encodedMasterPassword);

        lenient().when(encryptionService.encodePassword(canonicalMasterPassword))
                .thenReturn(encodedMasterPassword.rawPassword());
        lenient().when(masterRepository.register(basicMasterLogin, encodedMasterPassword)).thenReturn(master);

        registrationService = new RegistrationService(masterRepository, encryptionService);
        Master successfulRegistration = registrationService.register(basicMasterLogin, canonicalMasterPassword);

        ArgumentCaptor<MasterPassword> passwordCaptor = ArgumentCaptor.forClass(MasterPassword.class);
        verify(masterRepository).register(eq(basicMasterLogin), passwordCaptor.capture());
        MasterPassword actualPassedPassword = passwordCaptor.getValue();

        assertEquals(encodedMasterPassword.rawPassword(), actualPassedPassword.rawPassword());
        assertEquals(master, successfulRegistration);
    }

    @Test
    @DisplayName("Testing registration of password without upper case")
    public void testPasswordMissingUpperCaseLetter() {
        final String upperCaseLetterMissingMessage = "Make sure the password contains at least one upper case letter.";

        assertMissingPasswordCriteriaException(missingUpperCaseMasterPassword, upperCaseLetterMissingMessage);
    }

    @Test
    public void testPasswordMissingDigit() {
        final String digitMissingMessage = "Make sure the password contains at least one digit.";

        assertMissingPasswordCriteriaException(missingDigitMasterPassword, digitMissingMessage);
    }

    @Test
    public void testPasswordMissingSpecialCharacter() {
        final String specialCharacterMissingMessage = "Make sure the password contains at least one special character.";

        assertMissingPasswordCriteriaException(missingSpecialCharacterMasterPassword, specialCharacterMissingMessage);
    }

    private void assertMissingPasswordCriteriaException(MasterPassword masterPassword, String expectedExceptionMessage) {
        master = new Master(basicMasterLogin, masterPassword);
        registrationService = new RegistrationService(masterRepository, encryptionService);
        lenient().when(masterRepository.register(basicMasterLogin, masterPassword)).thenReturn(master);

        PasswordNotCompleteException exception = assertThrows(PasswordNotCompleteException.class,
                () -> registrationService.register(basicMasterLogin, masterPassword));

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }
}
