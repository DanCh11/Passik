package de.daycu.passik.model.vault;

import lombok.NonNull;

/**
 * Represents the name of a digital service.
 * Ensures the name is not empty.
 *
 * @param name The name of the digital service.
 *
 * @throws IllegalArgumentException If name is empty.
 */
public record DigitalServiceName(@NonNull String name) {

    public DigitalServiceName {
        if (name.isEmpty()) throw new IllegalArgumentException("'name' must not be empty");
    }
}
