package auth;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import port.out.persistance.MasterRepository;
import service.auth.AuthenticationResult;
import service.auth.AuthenticationService;
import service.auth.MasterRealm;
import service.encryption.EncryptionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private MasterLogin masterLogin;
    private MasterPassword masterPassword;
    private MasterRealm masterRealm;
    @Mock private MasterRepository masterRepository;
    @Mock private EncryptionService encryptionService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        final String encodedPassword = "encodedPassword";
        masterLogin = new MasterLogin("masterLogin");
        masterPassword = new MasterPassword("masterPassword");
        masterRealm = new MasterRealm(masterRepository, encryptionService);
        MasterPassword encodedMasterPassword = new MasterPassword(encodedPassword);
        Master master = new Master(masterLogin, encodedMasterPassword);

        lenient().when(encryptionService.encodePassword(masterPassword)).thenReturn(encodedPassword);
        lenient().when(encryptionService.verifyPassword(masterPassword, encodedPassword)).thenReturn(true);
        lenient().when(masterRepository.getMasterByLogin(masterLogin)).thenReturn(master);
    }


    @Test
    @DisplayName("Testing successful authentication")
    public void testSuccessfulAuthentication() {
        authenticationService = new AuthenticationService(masterRealm);
        AuthenticationResult result = authenticationService.authenticate(masterLogin, masterPassword);

        assertTrue(result.success());
        assertEquals("Login was performed successfully.", result.message());
    }

    @Test
    @DisplayName("Testing account wrong credentials")
    public void testAccountWrongCredentials() {
        masterLogin = new MasterLogin("masterLogin");
        masterPassword = new MasterPassword("1234");
        authenticationService = new AuthenticationService(masterRealm);
        AuthenticationResult result = authenticationService.authenticate(masterLogin, masterPassword);

        assertFalse(result.success());
        assertEquals(
                "Invalid credentials provided. Please check your username and password.",
                result.message()
        );
    }

    @Test
    @DisplayName("Testing not registered account")
    public void testNotRegisteredAccountException() {
         masterLogin = new MasterLogin("unknownLogin");
         masterPassword = new MasterPassword("unknownPassword");
         authenticationService = new AuthenticationService(masterRealm);

         UnknownAccountException exception = assertThrows(UnknownAccountException.class,
                 () -> authenticationService.authenticate(masterLogin, masterPassword));

         assertEquals("Account is not registered.", exception.getMessage());
    }
}
