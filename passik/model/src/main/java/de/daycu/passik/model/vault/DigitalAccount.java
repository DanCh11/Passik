package de.daycu.passik.model.vault;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder(toBuilder = true)
public record DigitalAccount(DigitalServiceName digitalServiceName, Credentials credentials) {

    public DigitalAccount {
        Objects.requireNonNull(digitalServiceName, "'digital service name' shall not be null");
        Objects.requireNonNull(credentials, "'credentials' shall not be null");
    }
    
}
