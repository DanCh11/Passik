package de.daycu.passik.model.vault;

import de.daycu.passik.model.auth.Password;

import java.util.Objects;

public record CredentialPassword(String rawPassword) implements Password {

    public CredentialPassword {
        Objects.requireNonNull(rawPassword, "'rawPassword' must not be null");
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("'rawPassword' must not be empty");
    }
}
