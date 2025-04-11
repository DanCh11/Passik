package de.daycu.passik.model.vault;

import lombok.NonNull;

public record CredentialLogin(@NonNull String value) {

    public CredentialLogin {
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
