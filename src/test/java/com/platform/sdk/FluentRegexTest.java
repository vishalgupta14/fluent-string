package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FluentRegexTest {

    @Test
    void testExtractFirstMatch() {
        FluentString input = FluentString.of("abc123def456");
        Optional<String> match = input.extractFirstMatch("\\d+");
        assertTrue(match.isPresent());
        assertEquals("123", match.get());

        Optional<String> noMatch = input.extractFirstMatch("XYZ");
        assertFalse(noMatch.isPresent());
    }

    @Test
    void testExtractAllMatches() {
        FluentString input = FluentString.of("abc123def456ghi789");
        List<String> matches = input.extractAllMatches("\\d+");
        assertEquals(3, matches.size());
        assertEquals("123", matches.get(0));
        assertEquals("456", matches.get(1));
        assertEquals("789", matches.get(2));

        List<String> noMatches = input.extractAllMatches("XYZ");
        assertTrue(noMatches.isEmpty());
    }

    @Test
    void testCountMatches() {
        FluentString input = FluentString.of("one two one two one");
        int count = input.countMatches("one");
        assertEquals(3, count);

        int noCount = input.countMatches("three");
        assertEquals(0, noCount);
    }
}