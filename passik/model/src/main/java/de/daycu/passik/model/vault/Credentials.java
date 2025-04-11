package de.daycu.passik.model.vault;

import lombok.NonNull;

public record Credentials(@NonNull CredentialLogin credentialLogin, @NonNull CredentialPassword credentialPassword) { }
