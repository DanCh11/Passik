package de.daycu.passik.model.vault;

import de.daycu.passik.model.auth.MasterId;
import lombok.Builder;
import lombok.NonNull;


@Builder(toBuilder = true)
public record DigitalAccount(
        @NonNull MasterId masterId,
        @NonNull DigitalServiceName digitalServiceName,
        @NonNull Credentials credentials) { }
