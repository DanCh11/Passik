package de.daycu.passik.model.vault;

import lombok.NonNull;

/**
 * Represents the login and password for a specific digital account credential.
 *
 * @param credentialLogin The login identifier.
 * @param credentialPassword The password.
 */
public record Credentials(@NonNull CredentialLogin credentialLogin, @NonNull CredentialPassword credentialPassword) { }
