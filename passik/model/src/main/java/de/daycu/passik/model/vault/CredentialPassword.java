package de.daycu.passik.model.vault;

import de.daycu.passik.model.auth.Password;
import lombok.NonNull;

/**
 * Password for a specific credential.
 * Implements Password interface.
 *
 * @param rawPassword The raw password value.
 *
 * @throws IllegalArgumentException If rawPassword is empty.
 */
public record CredentialPassword(@NonNull String rawPassword) implements Password {

    public CredentialPassword {
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("'rawPassword' must not be empty");
    }
}
