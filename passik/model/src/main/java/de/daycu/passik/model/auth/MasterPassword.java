package de.daycu.passik.model.auth;

import java.util.Objects;

public record MasterPassword(String rawPassword) implements Password {
    
    public MasterPassword {
        Objects.requireNonNull(rawPassword, "'rawPassword' must not be null");
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("'rawPassword' must not be empty");
    }
}
