package com.platform.sdk.string;

/**
 * Provides assertion-based validation for FluentString instances.
 * Designed for defensive programming.
 */
public class FluentStringAssertions {

    private final FluentString fluentString;

    private FluentStringAssertions(FluentString fluentString) {
        this.fluentString = fluentString;
    }

    public static FluentStringAssertions assertThat(FluentString fluentString) {
        return new FluentStringAssertions(fluentString);
    }

    public FluentStringAssertions assertLengthBetween(int min, int max) {
        int len = fluentString.get().length();
        if (len < min || len > max) {
            throw new IllegalArgumentException("Length must be between " + min + " and " + max + ", but was " + len);
        }
        return this;
    }

    public FluentStringAssertions assertNotBlank(String message) {
        if (fluentString.get().trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return this;
    }

    public FluentStringAssertions assertContains(String substring, String message) {
        if (!fluentString.get().contains(substring)) {
            throw new IllegalArgumentException(message);
        }
        return this;
    }

    public FluentStringAssertions assertMatches(String regex, String message) {
        if (!fluentString.get().matches(regex)) {
            throw new IllegalArgumentException(message);
        }
        return this;
    }

    public FluentString get() {
        return fluentString;
    }
}
