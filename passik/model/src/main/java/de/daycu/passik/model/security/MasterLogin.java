package de.daycu.passik.model.security;

import java.util.Objects;

public record MasterLogin(String value) {

    public MasterLogin {
        Objects.requireNonNull(value, "'value' must not be null");
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
