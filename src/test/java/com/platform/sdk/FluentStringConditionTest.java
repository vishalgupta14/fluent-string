package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringConditionTest {

    @Test
    void testIfTrueConditionMet() {
        FluentString result = FluentString.of("error 404")
            .ifTrue(s -> s.contains("404"), s -> "Not Found")
            .elseTransform(s -> "OK");

        assertEquals("Not Found", result.get());
    }

    @Test
    void testIfTrueConditionNotMet() {
        FluentString result = FluentString.of("success")
            .ifTrue(s -> s.contains("404"), s -> "Not Found")
            .elseTransform(s -> "OK");

        assertEquals("OK", result.get());
    }
}
