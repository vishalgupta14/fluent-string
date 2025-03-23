package com.platform.sdk;

import com.platform.sdk.string.FluentStringParser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FluentStringParserTest {

    @Test
    public void testToInt() {
        assertEquals(Optional.of(123), new FluentStringParser("123").toInt());
        assertEquals(Optional.empty(), new FluentStringParser("abc").toInt());
    }

    @Test
    public void testToDouble() {
        assertEquals(Optional.of(123.45), new FluentStringParser("123.45").toDouble());
        assertEquals(Optional.empty(), new FluentStringParser("abc").toDouble());
    }

    @Test
    public void testToBoolean() {
        assertEquals(Optional.of(true), new FluentStringParser("true").toBoolean());
        assertEquals(Optional.of(false), new FluentStringParser("false").toBoolean());
        assertEquals(Optional.empty(), new FluentStringParser("yes").toBoolean());
    }

    @Test
    public void testToChar() {
        assertEquals(Optional.of('J'), new FluentStringParser("Java").toChar());
        assertEquals(Optional.empty(), new FluentStringParser("").toChar());
    }

    @Test
    public void testToList() {
        assertEquals(Optional.of(Arrays.asList("a", "b", "c")), new FluentStringParser("a,b,c").toList(","));
    }

    @Test
    public void testToMap() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("k1", "v1");
        expected.put("k2", "v2");
        assertEquals(Optional.of(expected), new FluentStringParser("k1:v1;k2:v2").toMap(";", ":"));
    }

    enum TestEnum { ONE, TWO }

    @Test
    public void testToEnum() {
        assertEquals(Optional.of(TestEnum.ONE), new FluentStringParser("one").toEnum(TestEnum.class));
        assertEquals(Optional.empty(), new FluentStringParser("three").toEnum(TestEnum.class));
    }

    @Test
    public void testToBigDecimal() {
        assertEquals(Optional.of(new BigDecimal("123.45")), new FluentStringParser("123.45").toBigDecimal());
        assertEquals(Optional.empty(), new FluentStringParser("notANumber").toBigDecimal());
    }

    @Test
    public void testIsNumeric() {
        assertTrue(new FluentStringParser("123").isNumeric());
        assertTrue(new FluentStringParser("-45.6").isNumeric());
        assertFalse(new FluentStringParser("abc").isNumeric());
    }

    @Test
    public void testIsBoolean() {
        assertTrue(new FluentStringParser("true").isBoolean());
        assertTrue(new FluentStringParser("FALSE").isBoolean());
        assertFalse(new FluentStringParser("maybe").isBoolean());
    }

    @Test
    public void testIsDate() {
        assertTrue(new FluentStringParser("2024-12-31").isDate("yyyy-MM-dd"));
        assertFalse(new FluentStringParser("31/12/2024").isDate("yyyy-MM-dd"));
    }

    @Test
    public void testToLocalDate() {
        Optional<LocalDate> date = new FluentStringParser("2024-03-22").toLocalDate("yyyy-MM-dd");
        assertTrue(date.isPresent());
        assertEquals(2024, date.get().getYear());
    }
}
