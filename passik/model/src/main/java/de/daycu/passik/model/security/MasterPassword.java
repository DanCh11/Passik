package de.daycu.passik.model.security;

import java.util.Objects;

public record MasterPassword(String value) {
    
    public MasterPassword {
        Objects.requireNonNull(value, "'Value' must not be null");
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
