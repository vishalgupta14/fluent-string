package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import com.platform.sdk.string.FluentStringAssertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringAssertionsTest {

    @Test
    void testAssertLengthBetweenValid() {
        FluentStringAssertions.assertThat(FluentString.of("fluent"))
                .assertLengthBetween(3, 10);
    }

    @Test
    void testAssertLengthBetweenInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            FluentStringAssertions.assertThat(FluentString.of("hi"))
                    .assertLengthBetween(3, 10)
        );
        assertTrue(exception.getMessage().contains("Length must be between"));
    }

    @Test
    void testAssertNotBlankValid() {
        FluentStringAssertions.assertThat(FluentString.of("  not blank  "))
                .assertNotBlank("Should not be blank");
    }

    @Test
    void testAssertNotBlankThrows() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            FluentStringAssertions.assertThat(FluentString.of("   "))
                    .assertNotBlank("Blank string")
        );
        assertEquals("Blank string", exception.getMessage());
    }

    @Test
    void testAssertContainsPass() {
        FluentStringAssertions.assertThat(FluentString.of("hello world"))
                .assertContains("world", "Should contain 'world'");
    }

    @Test
    void testAssertContainsFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            FluentStringAssertions.assertThat(FluentString.of("hello"))
                    .assertContains("world", "Missing substring")
        );
        assertEquals("Missing substring", exception.getMessage());
    }

    @Test
    void testAssertMatchesPass() {
        FluentStringAssertions.assertThat(FluentString.of("abc123"))
                .assertMatches("\\w+\\d+", "Regex failed");
    }

    @Test
    void testAssertMatchesFail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            FluentStringAssertions.assertThat(FluentString.of("abc"))
                    .assertMatches("\\d+", "Must contain digits")
        );
        assertEquals("Must contain digits", exception.getMessage());
    }
}