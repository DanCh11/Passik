package de.daycu.passik.model.auth;

import java.util.Objects;

public record MasterPassword(String value) implements Password {
    
    public MasterPassword {
        Objects.requireNonNull(value, "'value' must not be null");
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }

    @Override
    public String getRawPassword() {
        return value;
    }
}
