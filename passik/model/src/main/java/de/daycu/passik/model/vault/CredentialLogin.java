package de.daycu.passik.model.vault;

import java.util.Objects;

public record CredentialLogin(String value) {

    public CredentialLogin {
        Objects.requireNonNull(value, "'value' must not be null");
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
