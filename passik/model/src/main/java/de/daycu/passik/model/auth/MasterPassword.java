package de.daycu.passik.model.auth;

import lombok.NonNull;


/**
 * Password for a master user, enforcing complexity rules.
 * Implements Password interface.
 *
 * @param rawPassword The raw password value.
 *
 * @throws IllegalArgumentException If rawPassword is empty.
 * @throws PasswordNotCompleteException If password doesn't meet complexity rules.
 */
public record MasterPassword(@NonNull String rawPassword) implements Password {
    
    public MasterPassword {
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("'rawPassword' must not be empty");

        final boolean hasUpperCase = rawPassword.matches(".*[A-Z].*");
        final boolean hasDigits = rawPassword.matches(".*\\d.*");
        final boolean hasSpecialCharacters = rawPassword.matches(".*[^a-zA-Z0-9].*");

        if (!hasUpperCase)
            throw new PasswordNotCompleteException("Make sure the password contains at least one upper case letter.");

        if (!hasDigits)
            throw new PasswordNotCompleteException("Make sure the password contains at least one digit.");

        if (!hasSpecialCharacters)
            throw new PasswordNotCompleteException("Make sure the password contains at least one special character.");
    }

}
