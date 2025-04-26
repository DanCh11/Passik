package de.daycu.passik.model.vault;

import lombok.NonNull;

/**
 * Represents the login for a specific credential.
 * Ensures the value is not empty.
 *
 * @param value The login value.
 *
 * @throws IllegalArgumentException If value is empty.
 */
public record CredentialLogin(@NonNull String value) {

    public CredentialLogin {
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
