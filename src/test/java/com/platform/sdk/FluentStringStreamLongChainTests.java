package com.platform.sdk;

import com.platform.sdk.string.stream.FluentStringStream;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class FluentStringStreamLongChainTests {

    @Test
    public void testVeryLongChain_CleanNormalizeCaseTransform() {
        String result = FluentStringStream.of("   Hello,  WORLD!! \u00C9xample  ")
                .trim()
                .normalize()
                .stripAccents()
                .toLowerCase()
                .capitalizeWords()
                .removePunctuation()
                .camelCase()
                .withPrefix("result:")
                .toString();

        assertEquals("result:helloWorldExample", result);
    }

    @Test
    public void testVeryLongChain_ComplexSlugConversion() {
        String result = FluentStringStream.of("Java  String  Stream     API 2025!!")
                .trim()
                .removeDigits()
                .removePunctuation()
                .toLowerCase()
                .toSlug()
                .withSuffix("-clean")
                .toString();

        assertEquals("java-string-stream-api-clean", result);
    }

    @Test
    public void testVeryLongChain_HeavyTextTransform() {
        String result = FluentStringStream.of("   tHis     IS     aN   Example    ")
                .trim()
                .removeDuplicateWords()
                .capitalizeWords()
                .snakeCase()
                .wrap("--")
                .toUpperCase()
                .toString();

        assertEquals("--THIS_IS_AN_EXAMPLE--", result);
    }

    @Test
    public void testVeryLongChain_StatisticsAnalysis() {
        FluentStringStream stream = FluentStringStream.of("one two TWO two three THREE three three");

        assertEquals(8, stream.wordCount());
        assertEquals(32, stream.charCount());
        assertEquals(3, stream.getWordFrequencyCaseSensitive().get("three"));
        assertTrue(stream.getCharFrequencyCaseSensitive().containsKey('o'));
    }

    @Test
    public void testVeryLongChain_EncodingDecodingOperations() {
        String original = "This is URL text!";

        String encoded = FluentStringStream.of(original).urlEncode();
        String decoded = FluentStringStream.of(encoded).urlDecode();

        assertEquals("This is URL text!", decoded);
    }

    @Test
    public void testVeryLongChain_IfBlankFallbacks() {
        String result = FluentStringStream.of("   ")
                .ifBlank("Default Value")
                .capitalize()
                .append("!")
                .toString();

        assertEquals("Default value!", result);
    }

    @Test
    public void testVeryLongChain_TitleCaseLocaleCompare() {
        String result = FluentStringStream.of("bonjour le monde")
                .toTitleCase(Locale.FRANCE);

        assertEquals("Bonjour Le Monde", result);

        int cmp = FluentStringStream.of("BONJOUR").compareIgnoreCase("bonjour", Locale.FRANCE);
        assertEquals(0, cmp);
    }
}
