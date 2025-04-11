package unit;

import de.daycu.passik.model.auth.Master;
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
import static utils.Fixtures.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock private MasterRepository masterRepository;
    @Mock private EncryptionService encryptionService;
    private MasterRealm masterRealm;
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        masterRealm = new MasterRealm(masterRepository, encryptionService);
        Master master = new Master(basicMasterLogin, encodedMasterPassword);

        lenient().when(encryptionService.encodePassword(basicMasterPassword))
                .thenReturn(encodedMasterPassword.rawPassword());
        lenient().when(encryptionService.verifyPassword(basicMasterPassword, encodedMasterPassword.rawPassword()))
                .thenReturn(true);
        lenient().when(masterRepository.getMasterByLogin(basicMasterLogin)).thenReturn(master);
    }


    @Test
    @DisplayName("Testing successful authentication")
    public void testSuccessfulAuthentication() {
        authenticationService = new AuthenticationService(masterRealm);
        AuthenticationResult result = authenticationService.authenticate(basicMasterLogin, basicMasterPassword);

        assertTrue(result.success());
        assertEquals("Login was performed successfully.", result.message());
    }

    @Test
    @DisplayName("Testing account wrong credentials")
    public void testAccountWrongCredentials() {
        authenticationService = new AuthenticationService(masterRealm);
        AuthenticationResult result = authenticationService.authenticate(basicMasterLogin, wrongMasterPassword);

        assertFalse(result.success());
        assertEquals(
                "Invalid credentials provided. Please check your username and password.",
                result.message()
        );
    }

    @Test
    @DisplayName("Testing not registered account")
    public void testNotRegisteredAccountException() {
        authenticationService = new AuthenticationService(masterRealm);

        UnknownAccountException exception = assertThrows(UnknownAccountException.class,
             () -> authenticationService.authenticate(unknownMasterLogin, unknownMasterPassword));

        assertEquals("Account is not registered.", exception.getMessage());
    }
}
