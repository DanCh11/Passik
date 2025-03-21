package de.daycu.passik.model.auth;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

public record Salt() {

    public static byte[] generate() {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return randomNumberGenerator.nextBytes().getBytes();
    }
}
