package registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
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

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock private MasterRepository masterRepository;
    @Mock private EncryptionService encryptionService;
    private RegistrationService registrationService;
    private MasterLogin masterLogin;
    private MasterPassword masterPassword;
    private Master master;

    @Test
    @DisplayName("Testing successful registration")
    public void testSuccessfulRegistration() {
        final String encodedPassword = "encodedPassword";

        masterLogin = new MasterLogin("masterLogin");
        masterPassword = new MasterPassword("MasterPassword1!");
        MasterPassword encodedMasterPassword = new MasterPassword(encodedPassword);
        master = new Master(masterLogin, encodedMasterPassword);

        lenient().when(encryptionService.encodePassword(masterPassword)).thenReturn(encodedPassword);
        lenient().when(masterRepository.register(masterLogin, encodedMasterPassword)).thenReturn(master);

        registrationService = new RegistrationService(masterRepository, encryptionService);
        Master successfulRegistration = registrationService.register(masterLogin, masterPassword);

        ArgumentCaptor<MasterPassword> passwordCaptor = ArgumentCaptor.forClass(MasterPassword.class);
        verify(masterRepository).register(eq(masterLogin), passwordCaptor.capture());
        MasterPassword actualPassedPassword = passwordCaptor.getValue();

        assertEquals(encodedPassword, actualPassedPassword.getRawPassword());
        assertEquals(master, successfulRegistration);
    }

    @Test
    @DisplayName("Testing registration of password without upper case")
    public void testPasswordMissingUpperCaseLetter() {
        final String upperCaseLetterMissingMessage = "Make sure the password contains at least one upper case letter.";
        masterPassword = new MasterPassword("masterpassword1!");

        assertMissingPasswordCriteriaException(masterPassword, upperCaseLetterMissingMessage);
    }

    @Test
    public void testPasswordMissingDigit() {
        final String digitMissingMessage = "Make sure the password contains at least one digit.";
        masterPassword = new MasterPassword("MasterPassword!");

        assertMissingPasswordCriteriaException(masterPassword, digitMissingMessage);
    }

    @Test
    public void testPasswordMissingSpecialCharacter() {
        final String specialCharacterMissingMessage = "Make sure the password contains at least one special character.";
        masterPassword = new MasterPassword("MasterPassword1");

        assertMissingPasswordCriteriaException(masterPassword, specialCharacterMissingMessage);
    }

    private void assertMissingPasswordCriteriaException(MasterPassword masterPassword, String expectedExceptionMessage) {
        masterLogin = new MasterLogin("masterLogin");
        master = new Master(masterLogin, masterPassword);
        registrationService = new RegistrationService(masterRepository, encryptionService);
        lenient().when(masterRepository.register(masterLogin, masterPassword)).thenReturn(master);

        PasswordNotCompleteException exception = assertThrows(PasswordNotCompleteException.class,
                () -> registrationService.register(masterLogin, masterPassword));

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }
}
