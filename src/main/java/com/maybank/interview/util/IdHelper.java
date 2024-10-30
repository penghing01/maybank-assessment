package com.maybank.interview.util;

import java.util.Random;

/**
 * Utility class for generating random IDs in various formats (Base62, Base36, and Base10),
 * as well as numeric and alphanumeric IDs based on the current date and time.
 */
public class IdHelper {

    private static final char[] base62Chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();
    private static final Random random = new Random();

    private IdHelper() {
    }

    /**
     * Generates a random Base36 string of the specified length.
     * Uses only numbers and uppercase letters for the generated string.
     *
     * @param length The length of the generated string.
     * @return A random Base36 string.
     */
    public static String getBase36(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(base62Chars[random.nextInt(36)]);
        }
        return stringBuilder.toString();
    }

    /**
     * Generates a random Base10 string of the specified length.
     * Uses only numeric characters (0-9) for the generated string.
     *
     * @param length The length of the generated string.
     * @return A random Base10 string.
     */
    public static String getBase10(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(base62Chars[random.nextInt(10)]);
        }
        return stringBuilder.toString();
    }

    /**
     * Generates a numeric ID based on the current date and time.
     * The format includes a short date (yyMMdd), seconds elapsed from midnight,
     * and a random 5-digit Base10 suffix.
     *
     * @return A formatted numeric ID string.
     */
    public static String generateNumericId() {
        return DateUtil.customNowDateAsString(DateUtil.SHORT_DATE_ONLY_FORMAT)
                + String.format("%05d", DateUtil.getSecondsElapsedFromZeroHour()) + getBase10(5);
    }
}
