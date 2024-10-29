package com.github.t1.jsonbap.runtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Optional;

public class DateTimeWriter {
    public static DateTimeFormatter dateTimeFormatter(Optional<Locale> l) {
        return l.map(locale -> new DateTimeFormatterBuilder().toFormatter(locale))
                .orElseGet(() -> new DateTimeFormatterBuilder().toFormatter());
    }

    public static DateFormat dateFormat(Optional<Locale> l) {
        return l.map(locale -> DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale))
                .orElseGet(SimpleDateFormat::new);
    }

    public static DateTimeFormatter dateTimeFormatter(String pattern, Optional<Locale> l) {
        return l.map(locale -> DateTimeFormatter.ofPattern(pattern, locale))
                .orElseGet(() -> DateTimeFormatter.ofPattern(pattern));
    }

    public static DateFormat dateFormat(String pattern, Optional<Locale> l) {
        return l.map(locale -> new SimpleDateFormat(pattern, locale))
                .orElseGet(() -> new SimpleDateFormat(pattern));
    }
}
