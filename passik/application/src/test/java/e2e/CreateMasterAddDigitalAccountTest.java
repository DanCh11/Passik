package e2e;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.vault.DigitalAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import port.out.persistance.DigitalAccountRepository;
import port.out.persistance.MasterRepository;
import service.auth.AppSecurityInitializer;
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

public class CreateMasterAddDigitalAccountTest {

    static MasterRepository masterRepository = new InMemoryMasterRepository();
    DigitalAccountRepository digitalAccountRepository = new InMemoryDigitalAccountRepository();
    static EncryptionService encryptionService = new EncryptionService();

    RegistrationService registrationService = new RegistrationService(masterRepository, encryptionService);
    DigitalAccountService digitalAccountService = new DigitalAccountService(digitalAccountRepository, encryptionService);
    AuthenticationService authenticationService = new AuthenticationService();

    @BeforeAll
    public static void init() {
        AppSecurityInitializer.initializeSecurity(masterRepository, encryptionService);
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
