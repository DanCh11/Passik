package de.daycu.passik.model.auth;

import lombok.NonNull;

public record MasterPassword(@NonNull String rawPassword) implements Password {
    
    public MasterPassword {
        if (rawPassword.isEmpty()) throw new IllegalArgumentException("'rawPassword' must not be empty");
    }
}
