package com.platform.sdk.string.stream;

import com.platform.sdk.string.FluentString;
import com.platform.sdk.string.FluentStringAssertions;
import com.platform.sdk.string.FluentStringBuilder;
import com.platform.sdk.string.FluentStringParser;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FluentStringStream {

    private final String source;
    private final List<Function<String, String>> pipeline;

    private FluentStringStream(String input) {
        this.source = input == null ? "" : input;
        this.pipeline = new ArrayList<>();
    }

    public static FluentStringStream of(String input) {
        return new FluentStringStream(input);
    }

    public FluentStringStream addStep(Function<String, String> transformation) {
        this.pipeline.add(transformation);
        return this;
    }

    public FluentStringStream trim() {
        return addStep(String::trim);
    }

    public FluentStringStream toLowerCase() {
        return addStep(s -> s.toLowerCase());
    }

    public FluentStringStream toUpperCase() {
        return addStep(s -> s.toUpperCase());
    }

    public FluentStringStream append(String str) {
        return addStep(s -> s + (str == null ? "" : str));
    }

    public FluentStringStream prepend(String str) {
        return addStep(s -> (str == null ? "" : str) + s);
    }

    public FluentStringStream replace(String target, String replacement) {
        return map(s -> s.replace(target, replacement));
    }

    public FluentStringStream replaceAll(String regex, String replacement) {
        return map(s -> s.replaceAll(regex, replacement));
    }

    public FluentStringStream replaceFirst(String regex, String replacement) {
        return map(s -> s.replaceFirst(regex, replacement));
    }

    public FluentStringStream removeDuplicateWords() {
        return addStep(s -> {
            Set<String> seen = new LinkedHashSet<>();
            StringBuilder sb = new StringBuilder();
            for (String word : s.trim().split("\\s+")) {
                if (seen.add(word)) {
                    sb.append(word).append(" ");
                }
            }
            return sb.toString().trim();
        });
    }

    public FluentStringStream reverse() {
        return map(s -> new StringBuilder(s).reverse().toString());
    }

    public FluentStringStream capitalize() {
        return map(s -> s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase());
    }

    public FluentStringStream removeWhitespace() {
        return map(s -> s.replaceAll("\\s+", ""));
    }

    public FluentStringStream clean() {
        return map(s -> s.replaceAll("[^a-zA-Z0-9 ]", "").trim());
    }

    public FluentStringStream capitalizeWords() {
        return map(s -> {
            String[] words = s.trim().split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    sb.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase()).append(" ");
                }
            }
            return sb.toString().trim();
        });
    }

    public FluentStringStream snakeCase() {
        return map(s -> s.trim().toLowerCase().replaceAll("\\s+", "_"));
    }

    public FluentStringStream kebabCase() {
        return map(s -> s.trim().toLowerCase().replaceAll("\\s+", "-"));
    }

    public FluentStringStream camelCase() {
        return map(s -> {
            String[] parts = s.toLowerCase().split("\\s+");
            StringBuilder sb = new StringBuilder(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                if (parts[i].length() > 0) {
                    sb.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
                }
            }
            return sb.toString();
        });
    }

    public FluentStringStream reverseWords() {
        return map(s -> {
            String[] words = s.trim().split("\\s+");
            List<String> wordList = new ArrayList<>(Arrays.asList(words));
            Collections.reverse(wordList);
            return String.join(" ", wordList);
        });
    }

    public FluentStringStream titleCase() {
        return map(s -> {
            String[] words = s.trim().split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    sb.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase()).append(" ");
                }
            }
            return sb.toString().trim();
        });
    }

    public FluentStringStream stripAccents() {
        return map(s -> Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
    }

    public FluentStringStream toSlug() {
        return map(s -> s.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", ""));
    }

    public FluentStringStream removeDigits() {
        return map(s -> s.replaceAll("\\d", ""));
    }

    public FluentStringStream removePunctuation() {
        return map(s -> s.replaceAll("[\\p{Punct}]", ""));
    }

    public FluentStringStream removeSpecialChars() {
        return map(s -> s.replaceAll("[^a-zA-Z0-9 ]", ""));
    }

    public FluentStringStream keepOnly(String allowedChars) {
        return map(s -> {
            StringBuilder result = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (allowedChars.indexOf(c) >= 0) {
                    result.append(c);
                }
            }
            return result.toString();
        });
    }

    public String collect() {
        String value = source;
        for (Function<String, String> op : pipeline) {
            value = op.apply(value);
        }
        return value;
    }

    public FluentStringStream normalize() {
        return map(s -> Normalizer.normalize(s, Normalizer.Form.NFC));
    }

    public FluentStringStream center(int width, char padChar) {
        return map(s -> {
            if (s.length() >= width) return s;
            int totalPadding = width - s.length();
            int left = totalPadding / 2;
            int right = totalPadding - left;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < left; i++) sb.append(padChar);
            sb.append(s);
            for (int i = 0; i < right; i++) sb.append(padChar);
            return sb.toString();
        });
    }

    public FluentStringStream indent(int spaces) {
        return map(s -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < spaces; i++) sb.append(' ');
            return sb.append(s).toString();
        });
    }

    public FluentStringStream padLeft(int length, char padChar) {
        return map(s -> {
            if (s.length() >= length) return s;
            StringBuilder sb = new StringBuilder();
            for (int i = s.length(); i < length; i++) sb.append(padChar);
            sb.append(s);
            return sb.toString();
        });
    }

    public FluentStringStream padRight(int length, char padChar) {
        return map(s -> {
            if (s.length() >= length) return s;
            StringBuilder sb = new StringBuilder(s);
            for (int i = s.length(); i < length; i++) sb.append(padChar);
            return sb.toString();
        });
    }

    public FluentStringStream truncate(int maxLength, String ellipsis) {
        return map(s -> {
            if (s.length() <= maxLength) return s;
            return s.substring(0, Math.max(0, maxLength - ellipsis.length())) + ellipsis;
        });
    }

    public FluentStringStream truncateWords(int n) {
        return map(s -> {
            if (n <= 0) return "";
            String[] words = s.trim().split("\\s+");
            if (words.length <= n) return s;
            return String.join(" ", Arrays.copyOfRange(words, 0, n));
        });
    }

    public FluentStringStream wrap(String wrapper) {
        return map(s -> wrapper + s + wrapper);
    }

    public FluentStringStream withPrefix(String prefix) {
        return map(s -> s.startsWith(prefix) ? s : prefix + s);
    }

    public FluentStringStream withSuffix(String suffix) {
        return map(s -> s.endsWith(suffix) ? s : s + suffix);
    }

    public int countOccurrences(String substring) {
        String value = collect();
        if (substring == null || substring.isEmpty()) return 0;
        int count = 0, index = 0;
        while ((index = value.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        return count;
    }

    public int charCount() {
        return collect().replaceAll("\\s+", "").length();
    }

    public int wordCount() {
        String val = collect().trim();
        return val.isEmpty() ? 0 : val.split("\\s+").length;
    }

    public Map<Character, Integer> getCharFrequencyCaseSensitive() {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (char c : collect().toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    public Map<String, Integer> getWordFrequencyCaseSensitive() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String[] words = collect().trim().split("\\s+"); // No .toLowerCase()
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        return map;
    }

    public Map<Character, Integer> getCharFrequencyIgnoreCase() {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (char c : collect().toLowerCase().toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    public Map<String, Integer> getWordFrequencyIgnoreCase() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String[] words = collect().trim().toLowerCase().split("\\s+");
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        return map;
    }

    public boolean isEmpty() {
        return collect().isEmpty();
    }

    public boolean isBlank() {
        return collect().trim().isEmpty();
    }

    public boolean equalsIgnoreCase(String other) {
        return collect().equalsIgnoreCase(other);
    }

    public boolean matches(String regex) {
        return collect().matches(regex);
    }

    public boolean isAlpha() {
        return collect().matches("[a-zA-Z]+");
    }

    public boolean isNumeric() {
        return collect().matches("\\d+");
    }

    public boolean isAlphaNumeric() {
        return collect().matches("[a-zA-Z0-9]+");
    }

    public boolean isEmail() {
        return collect().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean isXml() {
        try {
            javax.xml.parsers.DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new org.xml.sax.InputSource(new java.io.StringReader(collect())));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUpperCase() {
        String val = collect();
        return !val.isEmpty() && val.equals(val.toUpperCase());
    }

    public boolean isLowerCase() {
        String val = collect();
        return !val.isEmpty() && val.equals(val.toLowerCase());
    }

    public boolean isPalindrome() {
        String val = collect().replaceAll("[\\W_]", "").toLowerCase();
        return new StringBuilder(val).reverse().toString().equals(val);
    }

    public boolean startsWith(String prefix) {
        return collect().startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return collect().endsWith(suffix);
    }

    public boolean contains(String substring) {
        return collect().contains(substring);
    }

    public boolean hasLength(int expectedLength) {
        return collect().length() == expectedLength;
    }

    public boolean hasOnlyWhitespace() {
        String val = collect();
        return !val.isEmpty() && val.trim().isEmpty();
    }
    public FluentStringStream map(Function<String, String> transformation) {
        pipeline.add(transformation);
        return this;
    }

    // 🔄 Transform & Fallback
    public FluentStringStream orElse(String fallback) {
        return map(s -> s == null || s.trim().isEmpty() ? fallback : s);
    }

    public FluentStringStream orEmpty() {
        return map(s -> s == null || s.trim().isEmpty() ? "" : s);
    }

    public FluentStringStream ifEmpty(String fallback) {
        return map(s -> s.isEmpty() ? fallback : s);
    }

    public FluentStringStream ifBlank(String fallback) {
        return map(s -> s.trim().isEmpty() ? fallback : s);
    }

    public FluentStringStream ifCondition(Predicate<String> predicate, Function<String, String> fn) {
        return map(s -> predicate.test(s) ? fn.apply(s) : s);
    }

    public FluentStringStream safe(Function<String, String> fn) {
        return map(s -> {
            try {
                return fn.apply(s);
            } catch (Exception e) {
                return s;
            }
        });
    }

    public FluentStringStream transform(Function<String, String> fn) {
        return map(fn);
    }

    // 🔎 Extraction
    public Optional<String> extractFirstMatch(String regex) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(collect());
        return matcher.find() ? Optional.of(matcher.group()) : Optional.empty();
    }

    public List<String> extractAllMatches(String regex) {
        List<String> matches = new ArrayList<>();
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(collect());
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    public int countMatches(String regex) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(collect());
        int count = 0;
        while (matcher.find()) count++;
        return count;
    }

    // 🔧 Conversion
    public FluentStringParser convert() {
        return new FluentStringParser(collect());
    }

    public String toBase64() {
        return Base64.getEncoder().encodeToString(collect().getBytes(StandardCharsets.UTF_8));
    }

    public String fromBase64() {
        return new String(Base64.getDecoder().decode(collect()), StandardCharsets.UTF_8);
    }

    public String urlEncode() {
        try {
            return URLEncoder.encode(collect(), StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new RuntimeException("URL encoding failed", e);
        }
    }

    public String urlDecode() {
        try {
            return URLDecoder.decode(collect(), StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new RuntimeException("URL decoding failed", e);
        }
    }

    // 🔠 Others
    public List<String> lines() {
        return Arrays.asList(collect().split("\\R"));
    }

    public int lineCount() {
        return lines().size();
    }

    public String removeBlankLines() {
        return lines().stream().filter(line -> !line.trim().isEmpty()).collect(Collectors.joining(System.lineSeparator()));
    }

    public List<String> split(String regex) {
        return Arrays.asList(collect().split(regex));
    }

    public List<String> words() {
        return split("\\s+");
    }

    public static String join(List<String> parts, String delimiter) {
        return String.join(delimiter, parts);
    }

    public FluentStringStream debug() {
        System.out.println("FluentStringStream: " + collect());
        return this;
    }

    public FluentStringStream peek(Consumer<String> consumer) {
        consumer.accept(collect());
        return this;
    }

    public FluentStringStream validate(Predicate<String> predicate, String errorMessage) {
        if (!predicate.test(collect())) {
            throw new IllegalArgumentException(errorMessage);
        }
        return this;
    }

    public Optional<FluentStringStream> filter(Predicate<String> predicate) {
        return predicate.test(collect()) ? Optional.of(this) : Optional.empty();
    }

    public String apply(Function<String, String> plugin) {
        return plugin.apply(collect());
    }

    public FluentStringAssertions assertThat() {
        return FluentStringAssertions.assertThat(toFluentString());
    }

    public FluentStringBuilder toBuilder() {
        return FluentStringBuilder.start().append(collect());
    }

    public String toTitleCase(Locale locale) {
        String[] words = collect().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(word.substring(0, 1).toUpperCase(locale))
                        .append(word.substring(1).toLowerCase(locale))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    public int compareIgnoreCase(String other, Locale locale) {
        return collect().toLowerCase(locale).compareTo(other.toLowerCase(locale));
    }

    public FluentString toFluentString() {
        return FluentString.of(collect());
    }

    @Override
    public String toString() {
        return collect();
    }
} 
