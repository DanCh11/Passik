package registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import port.out.persistance.MasterRepository;
import service.registration.PasswordNotCompleteException;
import service.registration.RegistrationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private MasterRepository masterRepository;
    private RegistrationService registrationService;
    private MasterLogin masterLogin;
    private MasterPassword masterPassword;
    private Master master;

    @Test
    @DisplayName("Testing successful registration")
    public void testSuccessfulRegistration() {
        masterLogin = new MasterLogin("masterLogin");
        masterPassword = new MasterPassword("MasterPassword1!");
        master = new Master(masterLogin, masterPassword);
        registrationService = new RegistrationService(masterRepository);
        lenient().when(masterRepository.register(masterLogin, masterPassword)).thenReturn(master);
        Master successfulRegistration = registrationService.register(masterLogin, masterPassword);

        assertEquals(successfulRegistration, master);
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
        registrationService = new RegistrationService(masterRepository);
        lenient().when(masterRepository.register(masterLogin, masterPassword)).thenReturn(master);

        PasswordNotCompleteException exception = assertThrows(PasswordNotCompleteException.class,
                () -> registrationService.register(masterLogin, masterPassword));

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }
}
