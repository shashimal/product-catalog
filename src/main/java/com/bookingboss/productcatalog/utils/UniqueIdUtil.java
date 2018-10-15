package com.bookingboss.productcatalog.utils;

import java.util.UUID;

/**
 * This is a utility class which provides UniqueId related functions .
 *
 */
public class UniqueIdUtil {

    /**
     * Generate a UUID.
     *
     * @return the UUID as a string
     */
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

}
