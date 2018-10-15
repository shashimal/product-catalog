package com.bookingboss.productcatalog.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * This is a utility class which provides custom data functions
 */
public class DateUtil {

    /**
     * Obtains the current date-time from the system clock in UTC format.
     *
     * @return the current date-time using the system clock in UTC
     */
    public static LocalDateTime getTimestampInUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
