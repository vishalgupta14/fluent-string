package com.platform.sdk.string;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Utility class for safe type conversion from String.
 */
public class FluentStringParser {

    private final String source;

    public FluentStringParser(String source) {
        this.source = source == null ? "" : source.trim();
    }

    public Optional<Integer> toInt() {
        try {
            return Optional.of(Integer.parseInt(source));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> toLong() {
        try {
            return Optional.of(Long.parseLong(source));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Double> toDouble() {
        try {
            return Optional.of(Double.parseDouble(source));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Float> toFloat() {
        try {
            return Optional.of(Float.parseFloat(source));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<BigDecimal> toBigDecimal() {
        try {
            return Optional.of(new BigDecimal(source));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Optional<Boolean> toBoolean() {
        String s = source.toLowerCase();
        if ("true".equals(s)) return Optional.of(true);
        if ("false".equals(s)) return Optional.of(false);
        return Optional.empty();
    }

    public Optional<Character> toChar() {
        return source.isEmpty() ? Optional.empty() : Optional.of(source.charAt(0));
    }

    public Optional<List<String>> toList(String delimiter) {
        if (source.isEmpty()) return Optional.empty();
        return Optional.of(Arrays.asList(source.split(delimiter)));
    }

    public Optional<Map<String, String>> toMap(String entryDelimiter, String kvDelimiter) {
        if (source.isEmpty()) return Optional.empty();
        Map<String, String> map = new LinkedHashMap<>();
        String[] entries = source.split(entryDelimiter);
        for (String entry : entries) {
            String[] kv = entry.split(kvDelimiter, 2);
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }
        return Optional.of(map);
    }

    public <T extends Enum<T>> Optional<T> toEnum(Class<T> enumClass) {
        try {
            return Optional.of(Enum.valueOf(enumClass, source.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDate> toLocalDate(String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return Optional.of(LocalDate.parse(source, formatter));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDateTime> toLocalDateTime(String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return Optional.of(LocalDateTime.parse(source, formatter));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Instant> toInstant() {
        try {
            return Optional.of(Instant.parse(source));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean isNumeric() {
        return source.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean isBoolean() {
        return "true".equalsIgnoreCase(source) || "false".equalsIgnoreCase(source);
    }

    public boolean isDate(String pattern) {
        try {
            DateTimeFormatter.ofPattern(pattern).parse(source);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
