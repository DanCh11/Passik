package de.daycu.passik.model.auth;

import lombok.NonNull;

public record MasterLogin(@NonNull String value) {

    public MasterLogin {
        if (value.isEmpty()) throw new IllegalArgumentException("'value' must not be empty");
    }
}
