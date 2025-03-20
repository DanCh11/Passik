package de.daycu.passik.model.auth;

import java.util.Objects;

public record Master(MasterLogin masterLogin, MasterPassword masterPassword) {

    public Master {
        Objects.requireNonNull(masterLogin, "'masterLogin' shall not be null");
        Objects.requireNonNull(masterPassword, "'masterPassword' shall not be null");
    }
}
