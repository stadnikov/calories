package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate lt, Locale locale) {
            return lt.format(ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime lt, Locale locale) {
            return lt.format(ISO_LOCAL_TIME);
        }
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER_WITHOUT_T = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");

        @Override
        public LocalDateTime parse(String text, Locale locale) {
            return LocalDateTime.parse(text.replace(" ","T"));
        }

        @Override
        public String print(LocalDateTime lt, Locale locale) {
            return lt.format(FORMATTER_WITHOUT_T);
        }
    }
}
