package com.platform.sdk.string;

import org.apache.commons.text.StringEscapeUtils;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * FluentString is a chainable, immutable utility for fluent and readable
 * string manipulation.
 */
public final class FluentString {
    private final String input;
    private final String result;

    private FluentString(String input, String result) {
        this.input = input == null ? "" : input;
        this.result = result == null ? "" : result;
    }

    public static FluentString of(String input) {
        return new FluentString(input, input);
    }

    public String get() {
        return result;
    }

    public String input() {
        return input;
    }

    public String result() {
        return result;
    }

    @Override
    public String toString() {
        return result;
    }

    // Example transformations
    public FluentString trim() {
        return new FluentString(input, result.trim());
    }

    public FluentString toLowerCase() {
        return new FluentString(input, result.toLowerCase());
    }

    public FluentString toUpperCase() {
        return new FluentString(input, result.toUpperCase());
    }

    public FluentString append(String str) {
        return new FluentString(input, result + (str == null ? "" : str));
    }

    public FluentString substring(int beginIndex) {
        return new FluentString(input, result.substring(beginIndex));
    }

    public FluentString substring(int beginIndex, int endIndex) {
        return new FluentString(input, result.substring(beginIndex, endIndex));
    }

    public FluentString prepend(String str) {
        return new FluentString(input, (str == null ? "" : str) + result);
    }

    public FluentString replace(String target, String replacement) {
        return new FluentString(input, result.replace(target, replacement));
    }

    public FluentString replaceAll(String regex, String replacement) {
        return new FluentString(input, result.replaceAll(regex, replacement));
    }

    public FluentString replaceFirst(String regex, String replacement) {
        return new FluentString(input, result.replaceFirst(regex, replacement));
    }

    public FluentString reverse() {
        return new FluentString(input, new StringBuilder(result).reverse().toString());
    }

    public FluentString capitalize() {
        if (result.isEmpty()) return this;
        return new FluentString(input, Character.toUpperCase(result.charAt(0)) + result.substring(1).toLowerCase());
    }

    public FluentString removeWhitespace() {
        return new FluentString(input, result.replaceAll("\\s+", ""));
    }

    public FluentString padLeft(int length, char padChar) {
        if (result.length() >= length) return this;
        StringBuilder sb = new StringBuilder();
        for (int i = result.length(); i < length; i++) {
            sb.append(padChar);
        }
        sb.append(result);
        return new FluentString(input, sb.toString());
    }

    public FluentString padRight(int length, char padChar) {
        if (result.length() >= length) return this;
        StringBuilder sb = new StringBuilder(result);
        for (int i = result.length(); i < length; i++) {
            sb.append(padChar);
        }
        return new FluentString(input, sb.toString());
    }

    public FluentString truncate(int maxLength, String ellipsis) {
        if (result.length() <= maxLength) return this;
        return new FluentString(input, result.substring(0, Math.max(0, maxLength - ellipsis.length())) + ellipsis);
    }

    public boolean isEmpty() {
        return result.isEmpty();
    }

    public boolean isBlank() {
        return result.trim().isEmpty();
    }

    public boolean contains(String str) {
        return result.contains(str);
    }

    public boolean startsWith(String prefix) {
        return result.startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return result.endsWith(suffix);
    }

    public int length() {
        return result.length();
    }

    public Optional<String> toOptional() {
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    public FluentString orElse(String fallback) {
        return isBlank() ? FluentString.of(fallback) : this;
    }

    public FluentString orEmpty() {
        return isBlank() ? FluentString.of("") : this;
    }

    public FluentString map(Function<String, String> fn) {
        return new FluentString(input, fn.apply(result));
    }

    public boolean matches(String regex) {
        return result.matches(regex);
    }

    public Optional<String> extractFirstMatch(String regex) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(result);
        return matcher.find() ? Optional.of(matcher.group()) : Optional.empty();
    }

    public List<String> extractAllMatches(String regex) {
        List<String> matches = new ArrayList<>();
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(result);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    public int countMatches(String regex) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(result);
        int count = 0;
        while (matcher.find()) count++;
        return count;
    }

    public boolean equalsIgnoreCase(String other) {
        return result.equalsIgnoreCase(other);
    }

    public FluentString wrap(String wrapper) {
        return new FluentString(input, wrapper + result + wrapper);
    }

    public FluentString withPrefix(String prefix) {
        return result.startsWith(prefix) ? this : new FluentString(input, prefix + result);
    }

    public FluentString withSuffix(String suffix) {
        return result.endsWith(suffix) ? this : new FluentString(input, result + suffix);
    }

    public FluentString removeDigits() {
        return new FluentString(input, result.replaceAll("\\d", ""));
    }

    public FluentString removePunctuation() {
        return new FluentString(input, result.replaceAll("[\\p{Punct}]", ""));
    }

    public FluentString removeSpecialChars() {
        return new FluentString(input, result.replaceAll("[^a-zA-Z0-9 ]", ""));
    }

    public FluentString capitalizeWords() {
        String[] words = result.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return new FluentString(input, sb.toString().trim());
    }

    public FluentString snakeCase() {
        return new FluentString(input, result.trim().toLowerCase().replaceAll("\\s+", "_"));
    }

    public FluentString kebabCase() {
        return new FluentString(input, result.trim().toLowerCase().replaceAll("\\s+", "-"));
    }

    public FluentString camelCase() {
        String[] parts = result.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                sb.append(Character.toUpperCase(parts[i].charAt(0)))
                        .append(parts[i].substring(1));
            }
        }
        return new FluentString(input, sb.toString());
    }

    public FluentString repeat(int times) {
        if (times <= 0) return new FluentString(input, "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(result);
        }
        return new FluentString(input, sb.toString());
    }

    public FluentString indent(int spaces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            sb.append(' ');
        }
        return new FluentString(input, sb + result);
    }

    public FluentString clean() {
        return new FluentString(input, result.replaceAll("[^a-zA-Z0-9 ]", "").trim());
    }

    public FluentString center(int width, char padChar) {
        if (result.length() >= width) return this;
        int totalPadding = width - result.length();
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paddingLeft; i++) sb.append(padChar);
        sb.append(result);
        for (int i = 0; i < paddingRight; i++) sb.append(padChar);
        return new FluentString(input, sb.toString());
    }

    public FluentString escapeHtml() {
        return new FluentString(input, StringEscapeUtils.escapeHtml4(result));
    }

    public FluentString escapeXml() {
        return new FluentString(input, StringEscapeUtils.escapeXml11(result));
    }

    public FluentString unescapeHtml() {
        return new FluentString(input, StringEscapeUtils.unescapeHtml4(result));
    }

    public FluentString unescapeXml() {
        return new FluentString(input, StringEscapeUtils.unescapeXml(result));
    }

    public FluentString getInitials() {
        String[] parts = result.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) sb.append(Character.toUpperCase(part.charAt(0)));
        }
        return new FluentString(input, sb.toString());
    }

    public static FluentString join(List<String> strings, String delimiter) {
        if (strings == null || strings.isEmpty()) return FluentString.of("");
        return FluentString.of(String.join(delimiter, strings));
    }

    public FluentString normalize() {
        return new FluentString(input, Normalizer.normalize(result, Normalizer.Form.NFC));
    }

    public FluentString padCenter(int length, char padChar) {
        return center(length, padChar);
    }

    public List<FluentString> split(String regex) {
        String[] parts = result.split(regex);
        List<FluentString> list = new ArrayList<>();
        for (String part : parts) {
            list.add(new FluentString(input, part));
        }
        return list;
    }

    public FluentString startCase() {
        return capitalizeWords();
    }

    public FluentString stripAccents() {
        String normalized = Normalizer.normalize(result, Normalizer.Form.NFD);
        return new FluentString(input, normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
    }

    public FluentString ifBlank(String fallback) {
        return isBlank() ? FluentString.of(fallback) : this;
    }

    public FluentString ifEmpty(String fallback) {
        return isEmpty() ? FluentString.of(fallback) : this;
    }

    public boolean isAlpha() {
        return result.matches("[a-zA-Z]+");
    }

    public boolean isNumeric() {
        return result.matches("\\d+");
    }

    public boolean isAlphaNumeric() {
        return result.matches("[a-zA-Z0-9]+");
    }

    public boolean isEmail() {
        return result.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean isXml() {
        try {
            DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(result)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public FluentString removeNonAlphaNumeric() {
        return new FluentString(input, result.replaceAll("[^a-zA-Z0-9]", ""));
    }

    public List<FluentString> words() {
        return split("\\s+");
    }

    public static FluentString format(String pattern, Object... args) {
        return new FluentString(String.format(pattern, args), String.format(pattern, args));
    }

    public FluentString transform(Function<String, String> fn) {
        return map(fn);
    }

    public FluentString peek(Consumer<String> consumer) {
        consumer.accept(result);
        return this;
    }

    public Optional<FluentString> filter(Predicate<String> predicate) {
        return predicate.test(result) ? Optional.of(this) : Optional.empty();
    }

    public FluentString validate(Predicate<String> predicate, String errorMessage) {
        if (!predicate.test(result)) {
            throw new IllegalArgumentException(errorMessage);
        }
        return this;
    }

    public FluentString ifCondition(Predicate<String> predicate, Function<String, String> fn) {
        return predicate.test(result) ? new FluentString(input, fn.apply(result)) : this;
    }

    public boolean isPalindrome() {
        String cleaned = result.replaceAll("[\\W_]", "").toLowerCase();
        return new StringBuilder(cleaned).reverse().toString().equals(cleaned);
    }

    public FluentStringCondition ifTrue(Predicate<String> condition, Function<String, String> transform) {
        return FluentStringCondition.ifTrue(this, condition, transform);
    }

    public FluentString toTitleCase(Locale locale) {
        String[] words = result.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                String firstChar = word.substring(0, 1).toUpperCase(locale);
                String rest = word.substring(1).toLowerCase(locale);
                sb.append(firstChar).append(rest).append(" ");
            }
        }
        return new FluentString(input, sb.toString().trim());
    }

    public int compareIgnoreCase(String other, Locale locale) {
        return result.toLowerCase(locale).compareTo(other.toLowerCase(locale));
    }

    public FluentStringBuilder toBuilder() {
        return FluentStringBuilder.start().append(this.result);
    }

    public static FluentStringBuilder builder() {
        return FluentStringBuilder.start();
    }

    public FluentStringAssertions assertThat() {
        return FluentStringAssertions.assertThat(this);
    }

    public FluentString apply(FluentStringPlugin plugin) {
        return new FluentString(input, plugin.apply(result));
    }

    public FluentString toBase64() {
        return new FluentString(input,
                java.util.Base64.getEncoder().encodeToString(result.getBytes(StandardCharsets.UTF_8))
        );
    }

    public FluentString fromBase64() {
        byte[] decoded = java.util.Base64.getDecoder().decode(result);
        return new FluentString(input, new String(decoded, StandardCharsets.UTF_8));
    }

    public FluentString urlEncode() {
        try {
            return new FluentString(input,
                    java.net.URLEncoder.encode(result, StandardCharsets.UTF_8.toString())
            );
        } catch (Exception e) {
            throw new RuntimeException("URL encoding failed", e);
        }
    }

    public FluentString urlDecode() {
        try {
            return new FluentString(input,
                    java.net.URLDecoder.decode(result, StandardCharsets.UTF_8.toString())
            );
        } catch (Exception e) {
            throw new RuntimeException("URL decoding failed", e);
        }
    }

    public List<FluentString> lines() {
        String[] split = result.split("\\R"); // handles all line breaks
        List<FluentString> list = new ArrayList<>();
        for (String line : split) {
            list.add(new FluentString(input, line));
        }
        return list;
    }

    public int lineCount() {
        return lines().size();
    }

    public FluentString removeBlankLines() {
        return new FluentString(input,
                lines().stream()
                        .map(FluentString::get)
                        .filter(line -> !line.trim().isEmpty())
                        .collect(Collectors.joining(System.lineSeparator()))
        );
    }

    public boolean hasLength(int expectedLength) {
        return result.length() == expectedLength;
    }

    public boolean hasOnlyWhitespace() {
        return !result.isEmpty() && result.trim().isEmpty();
    }

    public int countOccurrences(String substring) {
        if (substring == null || substring.isEmpty()) return 0;
        int count = 0, index = 0;
        while ((index = result.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }

    public boolean isUpperCase() {
        return !result.isEmpty() && result.equals(result.toUpperCase());
    }

    public boolean isLowerCase() {
        return !result.isEmpty() && result.equals(result.toLowerCase());
    }

    public FluentString toSlug() {
        return new FluentString(input,
                result.toLowerCase()
                        .replaceAll("[^a-z0-9\\s]", "")
                        .replaceAll("\\s+", "-")
                        .replaceAll("-{2,}", "-")
                        .replaceAll("^-|-$", "")
        );
    }

    public FluentString keepOnly(String allowedChars) {
        if (allowedChars == null || allowedChars.isEmpty()) return new FluentString(input, "");
        StringBuilder sb = new StringBuilder();
        for (char c : result.toCharArray()) {
            if (allowedChars.indexOf(c) >= 0) {
                sb.append(c);
            }
        }
        return new FluentString(input, sb.toString());
    }

    public String getCharFrequency() {
        return getCharFrequencyCaseSensitive().entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\":" + e.getValue())
                .collect(Collectors.joining(",", "{", "}"));
    }

    public String getWordFrequency() {
        return getWordFrequencyCaseSensitive().entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\":" + e.getValue())
                .collect(Collectors.joining(",", "{", "}"));
    }

    public Map<Character, Integer> getCharFrequencyCaseSensitive() {
        Map<Character, Integer> frequencyMap = new LinkedHashMap<>();
        for (char c : result.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    public Map<String, Integer> getWordFrequencyCaseSensitive() {
        Map<String, Integer> wordFreq = new LinkedHashMap<>();
        String[] words = result.trim().split("\\s+"); // No toLowerCase
        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }
        return wordFreq;
    }

    public Map<Character, Integer> getCharFrequencyIgnoreCase() {
        Map<Character, Integer> frequencyMap = new LinkedHashMap<>();
        for (char c : result.toLowerCase().toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    public Map<String, Integer> getWordFrequencyIgnoreCase() {
        Map<String, Integer> wordFreq = new LinkedHashMap<>();
        String[] words = result.trim().toLowerCase().split("\\s+");
        for (String word : words) {
            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
        }
        return wordFreq;
    }


    public boolean isChanged() {
        return !input.equals(result);
    }

    public FluentString debug() {
        System.out.println("Input: " + input + " | Result: " + result);
        return this;
    }

    public FluentString safe(Function<String, String> fn) {
        try {
            return new FluentString(input, fn.apply(result));
        } catch (Exception e) {
            return this;
        }
    }

    public FluentString reverseWords() {
        String[] words = result.trim().split("\\s+");
        List<String> wordList = Arrays.asList(words);
        Collections.reverse(wordList);
        return new FluentString(input, String.join(" ", wordList));
    }

    public FluentString titleCase() {
        String[] words = result.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return new FluentString(input, sb.toString().trim());
    }

    public FluentString removeDuplicateWords() {
        Set<String> seen = new LinkedHashSet<>();
        StringBuilder sb = new StringBuilder();
        for (String word : result.trim().split("\\s+")) {
            if (seen.add(word)) {
                sb.append(word).append(" ");
            }
        }
        return new FluentString(input, sb.toString().trim());
    }

    public int wordCount() {
        if (result.trim().isEmpty()) return 0;
        return result.trim().split("\\s+").length;
    }

    public int charCount() {
        return result.replaceAll("\\s+", "").length();
    }

    public FluentString truncateWords(int n) {
        if (n <= 0) return new FluentString(input, "");
        String[] words = result.trim().split("\\s+");
        if (words.length <= n) return this;
        return new FluentString(input, String.join(" ", Arrays.copyOfRange(words, 0, n)));
    }

    public FluentStringParser convert() {
        return new FluentStringParser(this.result);
    }
}
