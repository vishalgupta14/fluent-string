package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import com.platform.sdk.string.stream.FluentStringStream;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FluentStringStreamTest {

    @Test
    void testBasicTransformations() {
        assertEquals("hello world", FluentStringStream.of("  Hello World  ").trim()
                .toLowerCase().collect());

        assertEquals("HELLO", FluentStringStream.of("hello")
                .toUpperCase().collect());

        assertEquals("Hello!!!", FluentStringStream.of("Hello")
                .append("!!!").collect());

        assertEquals("!!!Hello", FluentStringStream.of("Hello")
                .prepend("!!!").collect());
    }

    @Test
    void testReplaceAndReverse() {
        assertEquals("he77o", FluentStringStream.of("hello")
                .replace("l", "7").collect());

        assertEquals("olleh", FluentStringStream.of("hello")
                .reverse().collect());
    }

    @Test
    void testCapitalizeAndCasing() {
        assertEquals("Hello", FluentStringStream.of("hello")
                .capitalize().collect());

        assertEquals("Hello World", FluentStringStream.of("hello world")
                .capitalizeWords().collect());
    }

    @Test
    void testCamelSnakeKebab() {
        assertEquals("helloWorld", FluentStringStream.of("hello world")
                .camelCase().collect());

        assertEquals("hello_world", FluentStringStream.of("hello world")
                .snakeCase().collect());

        assertEquals("hello-world", FluentStringStream.of("hello world")
                .kebabCase().collect());
    }

    @Test
    void testCenterPadTruncate() {
        assertEquals("--abc--", FluentStringStream.of("abc")
                .center(7, '-').collect());

        assertEquals("00abc", FluentStringStream.of("abc")
                .padLeft(5, '0').collect());

        assertEquals("abc00", FluentStringStream.of("abc")
                .padRight(5, '0').collect());

        assertEquals("abc..", FluentStringStream.of("abcdef")
                .truncate(5, "..").collect());
    }

    @Test
    void testConditions() {
        assertTrue(FluentStringStream.of("   ").isBlank());
        assertTrue(FluentStringStream.of("").isEmpty());
        assertTrue(FluentStringStream.of("HELLO").isUpperCase());
        assertTrue(FluentStringStream.of("hello").isLowerCase());
        assertTrue(FluentStringStream.of("madam").isPalindrome());
    }

    @Test
    void testFrequencyStats() {
        assertEquals(2, FluentStringStream.of("hello world hello")
                .countOccurrences("hello"));

        assertEquals(10, FluentStringStream.of("hello world")
                .charCount());

        assertEquals(2, FluentStringStream.of("hello world")
                .wordCount());

        Map<Character, Integer> charFreq = FluentStringStream.of("aabbbcc")
                .getCharFrequencyCaseSensitive();
        assertEquals(2, charFreq.get('a'));
        assertEquals(3, charFreq.get('b'));
        assertEquals(2, charFreq.get('c'));

        Map<String, Integer> wordFreq = FluentStringStream.of("hi hi hello")
                .getWordFrequencyCaseSensitive();
        assertEquals(2, wordFreq.get("hi"));
        assertEquals(1, wordFreq.get("hello"));
    }

    @Test
    void testSplitWordsLines() {
        List<String> lines = FluentStringStream.of("line1\nline2\nline3")
                .lines();
        assertEquals(3, lines.size());

        List<String> words = FluentStringStream.of("Java is awesome")
                .words();
        assertEquals(3, words.size());
    }

    @Test
    void testToBase64FromBase64() {
        String encoded = FluentStringStream.of("Hello").toBase64();
        assertEquals("Hello", FluentStringStream.of(encoded).fromBase64());
    }

    @Test
    void testUrlEncoding() {
        String encoded = FluentStringStream.of("hello world").urlEncode();
        assertEquals("hello world", FluentStringStream.of(encoded).urlDecode());
    }

    @Test
    void testFluentStringConversion() {
        FluentString fs = FluentStringStream.of("test").toFluentString();
        assertEquals("test", fs.get());
    }
}