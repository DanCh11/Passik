package e2e;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.vault.DigitalAccount;
import org.junit.jupiter.api.Test;
import port.out.persistance.DigitalAccountRepository;
import port.out.persistance.MasterRepository;
import service.auth.AuthenticationResult;
import service.auth.AuthenticationService;
import service.encryption.Argon2Encoder;
import service.encryption.EncryptionService;
import service.registration.RegistrationService;
import service.vault.DigitalAccountService;
import utils.InMemoryDigitalAccountRepository;
import utils.InMemoryMasterRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static utils.Fixtures.*;

public class CreateMasterAddDigitalAccountTest {

     MasterRepository masterRepository = new InMemoryMasterRepository();
     DigitalAccountRepository digitalAccountRepository = new InMemoryDigitalAccountRepository();
     Argon2Encoder argon2Encoder = new Argon2Encoder();
     EncryptionService encryptionService = new EncryptionService(argon2Encoder);

     RegistrationService registrationService = new RegistrationService(masterRepository, encryptionService);
     DigitalAccountService digitalAccountService = new DigitalAccountService(digitalAccountRepository, encryptionService);
     AuthenticationService authenticationService = new AuthenticationService(masterRepository, encryptionService);

     @Test
     public void execute() {
         registerMaster();
         authenticateMaster();
         addDigitalAccount();
     }

     private void registerMaster() {
         Master registeredMaster = registrationService.register(basicMasterLogin, basicMasterPassword);
         Master master = masterRepository.getMasterByLogin(basicMasterLogin);
         assertEquals(registeredMaster, master);
         assertNotNull(masterRepository.findAll());
     }

     private void authenticateMaster() {
         final AuthenticationResult successfulAuthentication =
                 new AuthenticationResult(true, "Login was performed successfully.");
         AuthenticationResult authentication = authenticationService.authenticate(basicMasterLogin, basicMasterPassword);
         assertEquals(successfulAuthentication, authentication);
     }

     private void addDigitalAccount() {
         DigitalAccount savedDigitalAccount = digitalAccountService.save(digitalAccount);
         assertNotNull(digitalAccountRepository.findAllByMasterId(masterId));

         DigitalAccount inMemoryDigitalAccount = digitalAccountRepository.findByDigitalServiceName(
                 savedDigitalAccount.masterId(), savedDigitalAccount.digitalServiceName());
         assertEquals(savedDigitalAccount, inMemoryDigitalAccount);
     }

}
