package service.registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import lombok.AllArgsConstructor;
import port.in.registration.RegistrationUseCase;
import port.out.persistance.MasterRepository;
import service.encryption.EncryptionService;

import java.util.function.Function;

@AllArgsConstructor
public class RegistrationService implements RegistrationUseCase {

    public MasterRepository masterRepository;
    public EncryptionService encryptionService;

    private final Function<MasterPassword, MasterPassword> encodePassword =
            credentialPassword ->  {
                String encodedPassword = encryptionService.encodePassword(credentialPassword);
                return new MasterPassword(encodedPassword);
            };

    @Override
    public Master register(MasterLogin masterLogin, MasterPassword masterPassword) {
        isPasswordValid(masterPassword);
        MasterPassword encodedMasterPassword = encodePassword.apply(masterPassword);

        return masterRepository.register(masterLogin, encodedMasterPassword);
    }

    /**
     * TODO: find a library to have password validity check directly in records or interface.
     */
    private static void isPasswordValid(MasterPassword masterPassword) {
        final String masterPasswordValue = masterPassword.rawPassword();
        final boolean hasUpperCase = masterPasswordValue.matches(".*[A-Z].*");
        final boolean hasDigits = masterPasswordValue.matches(".*\\d.*");
        final boolean hasSpecialCharacters = masterPasswordValue.matches(".*[^a-zA-Z0-9].*");

        if (!hasUpperCase)
            throw new PasswordNotCompleteException("Make sure the password contains at least one upper case letter.");

        if (!hasDigits)
            throw new PasswordNotCompleteException("Make sure the password contains at least one digit.");

        if (!hasSpecialCharacters)
            throw new PasswordNotCompleteException("Make sure the password contains at least one special character.");
    }
}
