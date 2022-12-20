package com.bootcamp.util;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public class UtilMethods {
    public static String generateNumberAccount (int longitudCod) {
        final String chars = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        IntStream.range(0,longitudCod).forEach(x -> {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        });

        return sb.toString();
    }
}
