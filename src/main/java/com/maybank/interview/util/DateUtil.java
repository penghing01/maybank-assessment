package com.maybank.interview.util;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for handling date and time operations in the Asia/Kuala_Lumpur timezone.
 * Provides methods to format the current date and time, set specific time formats,
 * and calculate elapsed seconds from midnight.
 */
public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TIME_ZONE = "Asia/Kuala_Lumpur";
    public static final String SHORT_DATE_ONLY_FORMAT = "yyMMdd";

    private DateUtil() {
    }

    /**
     * Returns the current date and time formatted according to a custom pattern.
     * Uses a provided date format string to customize the output format.
     *
     * @param dateFormat The pattern used to format the date-time string.
     * @return The formatted current date-time string.
     */
    public static String customNowDateAsString(String dateFormat) {
        return ZonedDateTime.now(ZoneId.of(TIME_ZONE))
                .format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * Calculates the seconds elapsed from midnight (00:00:00) to the current time.
     * Computes the duration between zero hour and the current time in Asia/Kuala_Lumpur timezone.
     *
     * @return The number of seconds elapsed from midnight to the current time.
     */
    public static Integer getSecondsElapsedFromZeroHour() {
        ZonedDateTime zeroHour = ZonedDateTime.now(ZoneId.of(TIME_ZONE))
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(TIME_ZONE));
        return (int) Duration.between(zeroHour, now).getSeconds();
    }

    /**
     * Calculates the seconds elapsed from midnight (00:00:00) to a specified ZonedDateTime.
     * Computes the duration from zero hour to the time specified in the provided date.
     *
     * @param customDate The ZonedDateTime to calculate elapsed seconds from zero hour.
     * @return The number of seconds elapsed from midnight to the specified date and time.
     */
    public static Integer getSecondsElapsedFromZeroHourForDate(ZonedDateTime customDate) {
        ZonedDateTime zeroHour = customDate
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return (int) Duration.between(zeroHour, customDate).getSeconds();
    }
}
