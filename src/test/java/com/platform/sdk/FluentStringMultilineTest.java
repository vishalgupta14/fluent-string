package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringMultilineTest {

    private static final String TEXT_BLOCK = String.join(System.lineSeparator(),
            "line 1",
            "",
            "line 2",
            "   ",
            "line 3");

    @Test
    void testLines() {
        FluentString input = FluentString.of(TEXT_BLOCK);
        List<FluentString> lines = input.lines();

        assertEquals(5, lines.size());
        assertEquals("line 1", lines.get(0).get());
        assertEquals("", lines.get(1).get());
        assertEquals("line 2", lines.get(2).get());
        assertEquals("   ", lines.get(3).get());
        assertEquals("line 3", lines.get(4).get());
    }

    @Test
    void testLineCount() {
        FluentString input = FluentString.of(TEXT_BLOCK);
        assertEquals(5, input.lineCount());
    }

    @Test
    void testRemoveBlankLines() {
        FluentString input = FluentString.of(TEXT_BLOCK);
        FluentString cleaned = input.removeBlankLines();

        List<FluentString> lines = cleaned.lines();
        assertEquals(3, lines.size());
        assertEquals("line 1", lines.get(0).get());
        assertEquals("line 2", lines.get(1).get());
        assertEquals("line 3", lines.get(2).get());
    }
}
