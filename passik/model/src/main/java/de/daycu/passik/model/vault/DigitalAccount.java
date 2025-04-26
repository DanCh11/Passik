package de.daycu.passik.model.vault;

import de.daycu.passik.model.auth.MasterId;
import lombok.Builder;
import lombok.NonNull;

/**
 * Represents a digital account associated with a master user.
 * Contains the master ID, service name, and login credentials.
 * Uses Lombok's Builder for easy object creation and modification.
 */
@Builder(toBuilder = true)
public record DigitalAccount(
        @NonNull MasterId masterId,
        @NonNull DigitalServiceName digitalServiceName,
        @NonNull Credentials credentials) { }
