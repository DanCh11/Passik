package de.daycu.passik.model.vault;

import lombok.NonNull;

public record DigitalServiceName(@NonNull String name) {

    public DigitalServiceName {
        if (name.isEmpty()) throw new IllegalArgumentException("'name' must not be empty");
    }
}
