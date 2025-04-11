package de.daycu.passik.model.vault;

import lombok.Builder;
import lombok.NonNull;


@Builder(toBuilder = true)
public record DigitalAccount(@NonNull DigitalServiceName digitalServiceName, @NonNull Credentials credentials) { }
