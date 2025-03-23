package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringTestSuite {

    // ... existing test cases ...

    @Test void testGetCharFrequencyMap() {
        Map<Character, Integer> freq = FluentString.of("aabcc!").getCharFrequencyCaseSensitive();
        assertEquals(2, freq.get('a'));
        assertEquals(1, freq.get('b'));
        assertEquals(2, freq.get('c'));
        assertEquals(1, freq.get('!'));
    }

    @Test void testGetWordFrequencyMap() {
        Map<String, Integer> freq = FluentString.of("Hello world hello HELLO").getWordFrequencyIgnoreCase();
        assertEquals(3, freq.get("hello"));
        assertEquals(1, freq.get("world"));
    }

    @Test void testGetCharFrequencyString() {
        String result = FluentString.of("aab").getCharFrequency();
        assertEquals("{\"a\":2,\"b\":1}", result);
    }

    @Test void testGetWordFrequencyString() {
        String result = FluentString.of("hi hi there").getWordFrequency();
        assertEquals("{\"hi\":2,\"there\":1}", result);
    }
}