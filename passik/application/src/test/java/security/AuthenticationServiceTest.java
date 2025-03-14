package security;

import de.daycu.passik.model.security.Master;
import de.daycu.passik.model.security.MasterLogin;
import de.daycu.passik.model.security.MasterPassword;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import port.out.persistance.MasterRepository;
import service.security.AuthenticationResult;
import service.security.AuthenticationService;
import service.security.MasterRealm;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private MasterLogin masterLogin;
    private MasterPassword masterPassword;
    private MasterRealm masterRealm;
    @Mock
    private MasterRepository masterRepository;
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        masterLogin = new MasterLogin("masterLogin");
        masterPassword = new MasterPassword("masterPassword");
        Master master = new Master(masterLogin, masterPassword);
        masterRealm = new MasterRealm(masterRepository);
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
