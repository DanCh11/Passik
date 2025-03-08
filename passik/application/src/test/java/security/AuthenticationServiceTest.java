package security;

import de.daycu.passik.model.security.MasterLogin;
import de.daycu.passik.model.security.MasterPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.security.AuthenticationService;
import service.security.UserRealm;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    private MasterLogin masterLogin;
    private MasterPassword masterPassword;
    private UserRealm userRealm;
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        masterLogin = new MasterLogin("da");
        masterPassword = new MasterPassword("123");
        HashMap<String, String> users = new HashMap<>();
        users.put(masterLogin.toString(), masterPassword.toString());
        userRealm = new UserRealm(users);
    }

    @Test
    @DisplayName("Testing successful authentication")
    public void testSuccessfulAuthentication() {
        authenticationService = new AuthenticationService(masterLogin, masterPassword, userRealm);
        assertTrue(authenticationService.authenticate().success());
        assertEquals("Login was performed successfully.", authenticationService.authenticate().message());

    }

    @Test
    @DisplayName("Testing account wrong credentials")
    public void testAccountWrongCredentials() {
        masterLogin = new MasterLogin("da");
        masterPassword = new MasterPassword("1234");
        authenticationService = new AuthenticationService(masterLogin, masterPassword, userRealm);
        assertFalse(authenticationService.authenticate().success());
        assertEquals(
                "Invalid credentials provided. Please check your username and password.",
                authenticationService.authenticate().message());
    }
}
