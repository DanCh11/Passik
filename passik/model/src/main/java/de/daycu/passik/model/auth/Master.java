package de.daycu.passik.model.auth;

import lombok.NonNull;

/**
 * Represents a master user account.
 * Contains ID, login, and password.
 *
 * @param masterId Unique identifier.
 * @param masterLogin Login details.
 * @param masterPassword Password.
 */
public record Master(
        @NonNull MasterId masterId,
        @NonNull MasterLogin masterLogin,
        @NonNull MasterPassword masterPassword) { }
