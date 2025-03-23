package com.platform.sdk;

import com.platform.sdk.string.FluentString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FluentStringEncodingTest {

    @Test
    void testToBase64() {
        FluentString input = FluentString.of("hello world");
        FluentString encoded = input.toBase64();
        assertEquals("aGVsbG8gd29ybGQ=", encoded.get());
    }

    @Test
    void testFromBase64() {
        FluentString encoded = FluentString.of("aGVsbG8gd29ybGQ=");
        FluentString decoded = encoded.fromBase64();
        assertEquals("hello world", decoded.get());
    }

    @Test
    void testUrlEncode() {
        FluentString input = FluentString.of("https://example.com?q=fluent string");
        FluentString encoded = input.urlEncode();
        assertEquals("https%3A%2F%2Fexample.com%3Fq%3Dfluent+string", encoded.get());
    }

    @Test
    void testUrlDecode() {
        FluentString encoded = FluentString.of("https%3A%2F%2Fexample.com%3Fq%3Dfluent+string");
        FluentString decoded = encoded.urlDecode();
        assertEquals("https://example.com?q=fluent string", decoded.get());
    }
}