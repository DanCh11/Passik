package de.daycu.passik.model.vault;

import java.util.Objects;

public record DigitalServiceName(String name) {
    
    public DigitalServiceName {
        Objects.requireNonNull(name, "'name' must not be null");
        if (name.isEmpty()) throw new IllegalArgumentException("'name' must not be empty");
    }
}
