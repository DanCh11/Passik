package de.daycu.passik.model.vault;

import de.daycu.passik.model.auth.Password;
import lombok.NonNull;

public record CredentialPassword(@NonNull String rawPassword) implements Password {

    public CredentialPassword {
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("'rawPassword' must not be empty");
    }
}
