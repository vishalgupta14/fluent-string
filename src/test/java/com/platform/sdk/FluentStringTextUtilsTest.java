package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FluentStringTextUtilsTest {

    @Test
    public void testReverseWords() {
        FluentString fs = FluentString.of("Java is powerful");
        assertEquals("powerful is Java", fs.reverseWords().get());
    }

    @Test
    public void testTitleCase() {
        FluentString fs = FluentString.of("java is powerful");
        assertEquals("Java Is Powerful", fs.titleCase().get());
    }

    @Test
    public void testRemoveDuplicateWords() {
        FluentString fs = FluentString.of("this is is a test test");
        assertEquals("this is a test", fs.removeDuplicateWords().get());
    }

    @Test
    public void testWordCount() {
        assertEquals(2, FluentString.of("hello world").wordCount());
        assertEquals(3, FluentString.of("   many   spaced  words   ").wordCount());
        assertEquals(0, FluentString.of("").wordCount());
    }

    @Test
    public void testCharCount() {
        assertEquals(10, FluentString.of("hello world").charCount());
        assertEquals(15, FluentString.of("   many   spaced  words   ").charCount());
    }

    @Test
    public void testTruncateWords() {
        FluentString fs = FluentString.of("Java is awesome and fast");
        assertEquals("Java is awesome", fs.truncateWords(3).get());
        assertEquals("", fs.truncateWords(0).get());
        assertEquals("Java is awesome and fast", fs.truncateWords(10).get());
    }
}
