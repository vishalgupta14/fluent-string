package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import com.platform.sdk.string.FluentStringBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringBuilderTest {

    @Test
    void testBasicBuild() {
        String result = FluentStringBuilder.start()
                .append("Hello")
                .space()
                .append("World")
                .exclaim()
                .build();

        assertEquals("Hello World!", result);
    }

    @Test
    void testToFluentStringAndChain() {
        FluentString result = FluentStringBuilder.start()
                .append("fluent")
                .space()
                .append("builder")
                .toFluentString()
                .toUpperCase();

        assertEquals("FLUENT BUILDER", result.get());
    }

    @Test
    void testToBuilderFromFluentString() {
        String result = FluentString.of("start")
                .toBuilder()
                .space()
                .append("end")
                .dot()
                .build();

        assertEquals("start end.", result);
    }

    @Test
    void testEmptyBuilder() {
        FluentStringBuilder builder = FluentStringBuilder.start();
        assertTrue(builder.isEmpty());
        assertEquals(0, builder.length());
    }

    @Test
    void testClearBuilder() {
        FluentStringBuilder builder = FluentStringBuilder.start()
                .append("reset me")
                .clear();
        assertEquals("", builder.build());
    }
}
