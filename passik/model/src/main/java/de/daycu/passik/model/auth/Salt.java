package de.daycu.passik.model.auth;

import java.util.Random;

public record Salt() {

    public static String generate() {
        final int leftLimit = 97;
        final int rightLimit = 122;
        final int stringLength = 5;

        return new Random().ints(leftLimit, rightLimit + 1)
            .limit(stringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
