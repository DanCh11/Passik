package unit;

import de.daycu.passik.model.auth.Master;

import org.apache.shiro.authc.IncorrectCredentialsException;
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
import service.encryption.EncryptionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static utils.Fixtures.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock private MasterRepository masterRepository;
    @Mock private EncryptionService encryptionService;
    private AuthenticationService authenticationService;


    @BeforeEach
    void init() {
        Master master = new Master(masterId, basicMasterLogin, encodedMasterPassword);

        authenticationService = new AuthenticationService(masterRepository, encryptionService);

        lenient().when(encryptionService.encodePassword(basicMasterPassword))
                .thenReturn(encodedMasterPassword.rawPassword());
        lenient().when(encryptionService.verifyPassword(basicMasterPassword, encodedMasterPassword.rawPassword()))
                .thenReturn(true);
        lenient().when(masterRepository.getMasterByLogin(basicMasterLogin)).thenReturn(master);
    }

    @Test
    @DisplayName("Testing successful authentication")
    public void testSuccessfulAuthentication() {
        AuthenticationResult result = authenticationService.authenticate(basicMasterLogin, basicMasterPassword);

        assertTrue(result.success());
        assertEquals("Login was performed successfully.", result.message());
    }

    @Test
    @DisplayName("Testing account wrong credentials")
    public void testAccountWrongCredentials() {
        assertThrows(IncorrectCredentialsException.class,
                () -> authenticationService.authenticate(basicMasterLogin, wrongMasterPassword));
    }

    @Test
    @DisplayName("Testing not registered account")
    public void testNotRegisteredAccountException() {
        assertThrows(UnknownAccountException.class,
             () -> authenticationService.authenticate(unknownMasterLogin, unknownMasterPassword));
    }
}
