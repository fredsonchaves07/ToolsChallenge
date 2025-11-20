package tools.challenge.core.utils;

import java.security.SecureRandom;

public class CodigoGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CodigoGenerator() {
    }

    public static String generateNumericCode(int length) {
        long max = (long) Math.pow(10, length);
        long number = (long) (RANDOM.nextDouble() * max);
        return String.format("%0" + length + "d", number);
    }
}
