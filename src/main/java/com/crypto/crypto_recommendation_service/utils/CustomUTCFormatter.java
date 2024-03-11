package com.crypto.crypto_recommendation_service.utils;

import java.time.format.DateTimeFormatter;

public class CustomUTCFormatter {
    private static final DateTimeFormatter UTC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static DateTimeFormatter getFormatter() {
        return UTC_FORMATTER;
    }
}
