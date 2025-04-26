package unit;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterPassword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;
import service.registration.RegistrationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static utils.Fixtures.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

     @Mock private MasterRepository masterRepository;
     @Mock private EncryptionService encryptionService;

    @Test
     @DisplayName("Testing successful registration")
     public void testSuccessfulRegistration() {
        Master master = new Master(masterId, basicMasterLogin, encodedMasterPassword);

        lenient().when(encryptionService.encodePassword(basicMasterPassword))
                  .thenReturn(encodedMasterPassword.rawPassword());
        lenient().when(masterRepository.register(basicMasterLogin, encodedMasterPassword)).thenReturn(master);

        RegistrationService registrationService = new RegistrationService(masterRepository, encryptionService);
        Master successfulRegistration = registrationService.register(basicMasterLogin, basicMasterPassword);

        ArgumentCaptor<MasterPassword> passwordCaptor = ArgumentCaptor.forClass(MasterPassword.class);
        verify(masterRepository).register(eq(basicMasterLogin), passwordCaptor.capture());
        MasterPassword actualPassedPassword = passwordCaptor.getValue();

        assertEquals(encodedMasterPassword.rawPassword(), actualPassedPassword.rawPassword());
        assertEquals(master, successfulRegistration);
     }

}
