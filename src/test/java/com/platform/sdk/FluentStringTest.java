package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringTest {

    @Test
    void testChainedMethods() {
        String result = FluentString.of("  HeLLo World!  ")
                .trim()
                .toLowerCase()
                .replace("world", "Java")
                .capitalize()
                .append("🚀")
                .get();

        assertEquals("Hello java!🚀", result);
    }

    @Test
    void testPadLeft() {
        String result = FluentString.of("42").padLeft(5, '0').get();
        assertEquals("00042", result);
    }

    @Test
    void testTruncate() {
        String result = FluentString.of("This is a long text")
                .truncate(10, "...")
                .get();
        assertEquals("This is...", result);
    }
}
