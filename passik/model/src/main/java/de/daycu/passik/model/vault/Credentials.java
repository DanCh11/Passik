package de.daycu.passik.model.vault;

import java.util.Objects;

public record Credentials(CredentialLogin credentialLogin, CredentialPassword credentialPassword) {
    
    public Credentials {
        Objects.requireNonNull(credentialLogin, "'credential login' shall not be null");
        Objects.requireNonNull(credentialPassword, "'credential password' shall not be null");
    }
}
