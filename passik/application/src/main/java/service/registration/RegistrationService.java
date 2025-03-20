package service.registration;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import lombok.AllArgsConstructor;
import port.in.registration.RegistrationUseCase;
import port.out.persistance.MasterRepository;

@AllArgsConstructor
public class RegistrationService implements RegistrationUseCase {

    public MasterRepository masterRepository;

    @Override
    public Master register(MasterLogin masterLogin, MasterPassword masterPassword) {
        isPasswordValid(masterPassword);

        // TODO: add encryption of master password and the salt.
        // TODO: don't forget to save salt in database if necessary
        return masterRepository.register(masterLogin, masterPassword);
    }

    private static void isPasswordValid(MasterPassword masterPassword) {
        final String masterPasswordValue = masterPassword.value();
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
