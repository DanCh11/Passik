package de.daycu.passik.model.auth;

import lombok.NonNull;

public record Master(
        @NonNull MasterId masterId,
        @NonNull MasterLogin masterLogin,
        @NonNull MasterPassword masterPassword) { }
