package e2e;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.vault.DigitalAccount;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import port.out.persistance.DigitalAccountRepository;
import port.out.persistance.MasterRepository;
import service.auth.AppSecurityManager;
import service.auth.AuthenticationResult;
import service.auth.AuthenticationService;
import service.encryption.EncryptionService;
import service.registration.RegistrationService;
import service.vault.DigitalAccountService;
import utils.InMemoryDigitalAccountRepository;
import utils.InMemoryMasterRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.Fixtures.*;

@Disabled
/**
 * The test is running in isolation successfully, but there is overlapping between initiation of app security manager.
 * When two tests with creation of App Security Manager are running, the test uses first instantiated instance.
 * And even reset of the service doesn't help.
 */
public class CreateMasterAddDigitalAccountTest {

    MasterRepository masterRepository = new InMemoryMasterRepository();
    DigitalAccountRepository digitalAccountRepository = new InMemoryDigitalAccountRepository();
    EncryptionService encryptionService = new EncryptionService();

    RegistrationService registrationService = new RegistrationService(masterRepository, encryptionService);
    DigitalAccountService digitalAccountService = new DigitalAccountService(digitalAccountRepository, encryptionService);
    AuthenticationService authenticationService = new AuthenticationService();

    @BeforeEach
    void init() {
        SecurityUtils.setSecurityManager(null);
        AppSecurityManager.reset();
        AppSecurityManager.initialize(masterRepository, encryptionService);
    }

    @Test
    public void execute() {
        registerMaster();
        authenticateMaster();
        addDigitalAccount();
    }

    private void registerMaster() {
        Master registeredMaster = registrationService.register(basicMasterLogin, canonicalMasterPassword);
        Master master = masterRepository.getMasterByLogin(basicMasterLogin);
        assertEquals(registeredMaster, master);
        assertNotNull(masterRepository.findAll());
    }

    private void authenticateMaster() {
        final AuthenticationResult successfulAuthentication =
                new AuthenticationResult(true, "Login was performed successfully.");
        AuthenticationResult authentication = authenticationService.authenticate(basicMasterLogin, canonicalMasterPassword);
        assertEquals(successfulAuthentication, authentication);
    }

    private void addDigitalAccount() {
        DigitalAccount savedDigitalAccount = digitalAccountService.save(digitalAccount);
        assertNotNull(digitalAccountRepository.findAllByMasterId(digitalAccount.masterId()));

        DigitalAccount inMemoryDigitalAccount = digitalAccountRepository.findByDigitalServiceName(
                savedDigitalAccount.masterId(), savedDigitalAccount.digitalServiceName());
        assertEquals(savedDigitalAccount, inMemoryDigitalAccount);
    }
}
