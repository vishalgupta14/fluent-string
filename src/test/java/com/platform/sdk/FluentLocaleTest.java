package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class FluentLocaleTest {

    @Test
    void testToTitleCaseEnglish() {
        FluentString input = FluentString.of("hello world from java");
        FluentString result = input.toTitleCase(Locale.ENGLISH);
        assertEquals("Hello World From Java", result.get());
    }

    @Test
    void testToTitleCaseTurkish() {
        FluentString input = FluentString.of("istanbul büyükşehir belediyesi");
        FluentString result = input.toTitleCase(new Locale("tr", "TR"));
        assertEquals("İstanbul Büyükşehir Belediyesi", result.get());
    }

    @Test
    void testCompareIgnoreCaseEnglish() {
        FluentString input = FluentString.of("Hello");
        assertEquals(0, input.compareIgnoreCase("HELLO", Locale.ENGLISH));
        assertTrue(input.compareIgnoreCase("WORLD", Locale.ENGLISH) < 0);
    }

}
