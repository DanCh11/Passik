package de.daycu.passik.model.auth;

import lombok.NonNull;

import java.util.UUID;

/**
 * Unique identifier for a master user.
 * Uses UUID for global uniqueness.
 * @param id The unique UUID.
 */
public record MasterId(@NonNull UUID id) { }
