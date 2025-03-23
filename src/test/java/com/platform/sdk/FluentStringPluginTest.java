package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import com.platform.sdk.string.FluentStringPlugin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringPluginTest {

    static class MyPlugin implements FluentStringPlugin {
        @Override
        public String apply(String input) {
            return input.trim().toUpperCase();
        }
    }

    @Test
    void testApplyCustomPlugin() {
        FluentString input = FluentString.of("  hello plugin  ");
        FluentString result = input.apply(new MyPlugin());
        assertEquals("HELLO PLUGIN", result.get());
    }

    @Test
    void testApplyLambdaPlugin() {
        FluentString input = FluentString.of("data with spaces");
        FluentString result = input.apply(s -> s.replaceAll(" ", "_"));
        assertEquals("data_with_spaces", result.get());
    }

    @Test
    void testApplyEmptyPlugin() {
        FluentString input = FluentString.of("text");
        FluentString result = input.apply(s -> "");
        assertTrue(result.isEmpty());
    }
}