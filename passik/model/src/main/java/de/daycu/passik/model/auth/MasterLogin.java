package de.daycu.passik.model.auth;

import lombok.NonNull;

/**
 * Login identifier for a master user.
 * Ensures the value is not empty.
 * @param value The login value.
 * @throws IllegalArgumentException If value is empty.
 */
public record MasterLogin(@NonNull String value) {

    public MasterLogin {
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
