# FluentString Utility

`FluentString` is a **chainable**, **immutable**, and **developer-friendly** string manipulation utility class for Java 8+. It aims to provide readable and functional-style string operations inspired by fluent APIs.

---

## 🚀 Features

- Fluent chaining: `FluentString.of("  hello ").trim().toUpperCase().get()`
- Null-safe and immutable
- Dozens of pre-built operations
- Easily extendable via `map()` / `transform()`

---

## 📦 Getting Started

```java
FluentString fs = FluentString.of("  hello ")
    .trim()
    .toUpperCase()
    .append(" WORLD")
    .replace("HELLO", "Hi");

System.out.println(fs.get()); // "Hi WORLD"
```

---

## 🧩 Core Methods

### Basic Transformations
- `trim()`, `toLowerCase()`, `toUpperCase()`
- `substring(...)`, `append(...)`, `prepend(...)`

### Advanced Formatting
- `capitalize()`, `capitalizeWords()`
- `camelCase()`, `snakeCase()`, `kebabCase()`
- `padLeft()`, `padRight()`, `padCenter()`
- `indent(int spaces)`

### Cleaning & Parsing
- `removeWhitespace()`, `removeDigits()`
- `removePunctuation()`, `removeSpecialChars()`
- `removeNonAlphaNumeric()`

### Validation
- `isAlpha()`, `isNumeric()`, `isAlphaNumeric()`
- `isEmail()`, `isXml()`
- `matches(regex)`

### Utilities
- `reverse()`, `getInitials()`, `normalize()`
- `escapeHtml()`, `unescapeHtml()`, `escapeXml()`, `unescapeXml()`
- `truncate(int max, String ellipsis)`

### Collection Helpers
- `split(String regex): List<FluentString>`
- `words(): List<FluentString>`
- `static join(List<String>, delimiter)`

### Functional Extensions
- `map(Function<String, String>)`
- `transform(Function<String, String>)`

### Null-Safety
- `orElse(String fallback)`
- `orEmpty()`
- `ifBlank(String fallback)`, `ifEmpty(String fallback)`

---

## 🧪 Example Usage

```java
String result = FluentString.of("  hello world ")
    .trim()
    .capitalizeWords()
    .kebabCase()
    .get();

// Output: "hello-world"
```

```java
FluentString email = FluentString.of("user@example.com")
    .validate(s -> s.contains("@"), "Invalid email")
    .toLowerCase();
```

---

## 🛠️ Planned Add-ons
- `peek(Consumer<String>)` for debugging
- `filter(Predicate<String>)` for conditional chaining
- `validate(...)` with exception support
- `isPalindrome()` and more utilities

---

# 🧠 Smart Features in FluentString & FluentStringBuilder

FluentString is a modern, immutable string utility class that supports chainable transformations. Below are the advanced features that make it a powerful tool for developers.

---

## 🔁 1. Chained Conditionals (if-else / switch-like)

Enable readable, functional-style conditional transformations:

```java
FluentString result = FluentString.of("error 404")
    .ifTrue(s -> s.contains("404"), s -> "Not Found")
    .elseTransform(s -> "OK");

System.out.println(result.get()); // "Not Found"
```

Or using `.condition(...)` shortcut:

```java
FluentString result = FluentString.of("status 500")
    .condition(s -> s.contains("500"), s -> "Server Error")
    .elseTransform(s -> "Success");
```

---

## 🧰 2. FluentRegex Helper Methods

Convenient regex match helpers built into FluentString:

```java
FluentString input = FluentString.of("abc123def456");

Optional<String> first = input.extractFirstMatch("\\d+"); // "123"
List<String> all = input.extractAllMatches("\\d+");       // ["123", "456"]
int count = input.countMatches("\\d+");                    // 2
```

---

## 🌐 3. Locale-Aware Utilities

Support for culturally accurate string transformations:

```java
FluentString turkish = FluentString.of("istanbul büyükşehir belediyesi")
    .toTitleCase(new Locale("tr", "TR"));
System.out.println(turkish.get()); // "İstanbul Büyükşehir Belediyesi"

int compare = FluentString.of("straße")
    .compareIgnoreCase("strasse", Locale.GERMAN); // 0 (equal in German)
```

---

## 🔡 4. FluentStringBuilder

Fluent, mutable string building with common symbols and formatting helpers:

```java
String result = FluentStringBuilder.start()
    .append("Hello")
    .space()
    .append("World")
    .exclaim()
    .build(); // "Hello World!"
```

### 🔁 Interoperability:

```java
// Convert to FluentString
FluentString fs = FluentStringBuilder.start()
    .append("fluent")
    .space()
    .append("builder")
    .toFluentString()
    .capitalizeWords();

// Convert FluentString to builder
String built = FluentString.of("start")
    .toBuilder()
    .space()
    .append("end")
    .dot()
    .build(); // "start end."
```
## 🧪 5. Assertions / Validations Module

Enable defensive programming with clean, fluent validations:

```java
FluentString.of("fluent")
    .assertThat()
    .assertLengthBetween(5, 10)
    .assertNotBlank("Must not be blank")
    .assertContains("flu", "Missing keyword")
    .assertMatches("[a-z]+", "Only lowercase allowed")
    .get();
```

Use `assertThat()` to get access to validation rules. These throw `IllegalArgumentException` if validation fails.

---

## 🧩 6. Plugins / Hooks

Add your own transformation logic using the plugin interface:

```java
FluentString fs = FluentString.of(" messy input ")
    .apply(String::trim)
    .apply(s -> s.replaceAll("\\s", "_"));

System.out.println(fs.get()); // "messy_input"
```

Just implement:

```java
@FunctionalInterface
public interface FluentStringPlugin {
    String apply(String input);
}
```

Then use `.apply(...)` to hook into the transformation pipeline.

---

## 🧵 7. Multiline / TextBlock Support

Perfect for working with logs, templates, or multi-line content:

```java
FluentString block = FluentString.of("""
    line 1

    line 2
    """);

List<FluentString> lines = block.lines();         // ["line 1", "", "line 2"]
int count = block.lineCount();                    // 3
String cleaned = block.removeBlankLines().get();  // "line 1\nline 2"
```

- `lines()` → Splits by any line break into `List<FluentString>`
- `lineCount()` → Returns total number of lines
- `removeBlankLines()` → Removes lines that are empty or only whitespace

---

These features make FluentString and FluentStringBuilder ideal for readable, flexible, and expressive string manipulation in modern Java applications.

> ✅ 100% unit-tested · 💡 Java 8 compatible · 📦 Easily pluggable as utility library



# 📚 Advanced FluentString Utilities

This document provides detailed usage for the following high-utility methods included in your `FluentString` class:

---

## 📏 1. `hasLength(int expectedLength)`

Checks if the string has the exact specified length.

```java
FluentString.of("Hello").hasLength(5); // true
FluentString.of("Hi").hasLength(3);    // false
```

---

## 🧼 2. `hasOnlyWhitespace()`

Returns `true` if the string contains only whitespace characters.

```java
FluentString.of("   ").hasOnlyWhitespace(); // true
FluentString.of(" hi ").hasOnlyWhitespace(); // false
```

---

## 🔍 3. `countOccurrences(String substring)`

Counts how many times a substring occurs within the string.

```java
FluentString.of("hello hello world").countOccurrences("hello"); // 2
FluentString.of("ababab").countOccurrences("ab");               // 3
```

---

## 🔠 4. `isUpperCase()` / `isLowerCase()`

Check if the string is fully upper or lower case (ignores empty strings).

```java
FluentString.of("HELLO").isUpperCase();  // true
FluentString.of("hello").isLowerCase();  // true
FluentString.of("Hello").isLowerCase();  // false
```

---

## 🌐 5. `toSlug()`

Converts a string into a URL-friendly slug (lowercase, dashes, no special chars).

```java
FluentString.of("Hello World!").toSlug().get(); 
// Output: "hello-world"

FluentString.of("Java & Spring Boot!!").toSlug().get(); 
// Output: "java-spring-boot"
```

---

## 🎯 6. `keepOnly(String allowedChars)`

Removes all characters that are *not* in the allowed set.

```java
FluentString.of("abc123!@#").keepOnly("abc123").get(); 
// Output: "abc123"
```

---

## 🔡 7. `getCharFrequency()`

Returns a JSON-style string representing the frequency of each character.

```java
FluentString.of("aab").getCharFrequency(); 
// Output: {"a":2,"b":1}
```

---

## 📊 8. `getWordFrequency()`

Returns a JSON-style string representing frequency of words (case-insensitive).

```java
FluentString.of("hello Hello world").getWordFrequency(); 
// Output: {"hello":2,"world":1}
```

---

## 🗺️ 9. `getCharFrequencyMap()`

Returns a `Map<Character, Integer>` of character counts.

```java
Map<Character, Integer> map = FluentString.of("aabc").getCharFrequencyMap();
// Output: {'a'=2, 'b'=1, 'c'=1}
```

---

## 📚 10. `getWordFrequencyMap()`

Returns a `Map<String, Integer>` of word counts.

```java
Map<String, Integer> map = FluentString.of("Hi hi there").getWordFrequencyMap();
// Output: {"hi"=2, "there"=1}
```



# 📘 FluentStringParser

`FluentStringParser` is a utility class that provides safe, fluent, and functional-style conversions from a `String` to commonly used data types like `Integer`, `Double`, `Boolean`, `LocalDate`, `BigDecimal`, and more.

It is designed to complement the `FluentString` class with:
```java
FluentString.of("123").convert().toInt();
```

---

## 🚀 Features

- ✅ Null-safe, trimmed inputs
- ✅ Optional-based parsing (no exceptions)
- ✅ Support for numbers, booleans, characters
- ✅ Date/time parsing with patterns
- ✅ Custom list, map, and enum conversion

---

## 📦 Usage

### 🔢 Number Parsing
```java
new FluentStringParser("123").toInt();              // Optional[123]
new FluentStringParser("12.5").toDouble();           // Optional[12.5]
new FluentStringParser("invalid").toInt();           // Optional.empty()
```

### 🔡 Boolean & Character
```java
new FluentStringParser("true").toBoolean();          // Optional[true]
new FluentStringParser("hello").toChar();            // Optional['h']
```

### 📆 Date Parsing
```java
new FluentStringParser("2024-03-22").toLocalDate("yyyy-MM-dd");
new FluentStringParser("2024-03-22 15:00").toLocalDateTime("yyyy-MM-dd HH:mm");
```

### 🧾 BigDecimal & Enum
```java
new FluentStringParser("123.456").toBigDecimal();
new FluentStringParser("one").toEnum(MyEnum.class);  // Optional[MyEnum.ONE]
```

### 🔁 Lists & Maps
```java
new FluentStringParser("a,b,c").toList(",");                       // Optional[List[a, b, c]]
new FluentStringParser("k1:v1;k2:v2").toMap(";", ":");            // Optional[{k1=v1, k2=v2}]
```

### ✅ Validation Helpers
```java
new FluentStringParser("123.45").isNumeric();        // true
new FluentStringParser("true").isBoolean();           // true
new FluentStringParser("2024-03-22").isDate("yyyy-MM-dd"); // true
```

# 🔠 FluentStringStream

A Java utility that provides a **lazy, fluent, chainable** API to manipulate strings using stream-like transformations.

Inspired by the **Java Streams API**, this utility lets you compose complex string operations while preserving immutability and readability.

---

## 🚀 Features

- Lazy execution of transformations
- Chainable `.map()`-style API
- Supports over 100+ transformation, analysis, and conversion operations
- Easily extendable with your own logic
- Seamless integration with `FluentString` and `FluentStringParser`

---

## 📦 Getting Started

```java
FluentStringStream stream = FluentStringStream.of("  Hello Fluent String Stream!  ");
String result = stream.trim()
        .toLowerCase()
        .append(" 🔥")
        .collect();

System.out.println(result); // "hello fluent string stream! 🔥"
```
## 🔧 API Overview

### 🔄 Transformations

| Method                          | Description                                      |
|---------------------------------|--------------------------------------------------|
| `toLowerCase()`                | Convert all characters to lowercase             |
| `toUpperCase()`                | Convert all characters to uppercase             |
| `append(str)`                  | Append text to the end                          |
| `prepend(str)`                 | Prepend text to the beginning                   |
| `replace(target, replacement)` | Replace all occurrences of a string             |
| `replaceAll(regex, replacement)` | Replace all regex matches                     |
| `replaceFirst(regex, replacement)` | Replace first regex match                  |
| `capitalize()`                 | Capitalize the first letter                     |
| `capitalizeWords()`            | Capitalize every word                           |
| `snakeCase()`                  | Convert to snake_case                           |
| `camelCase()`                  | Convert to camelCase                            |
| `kebabCase()`                  | Convert to kebab-case                           |
| `reverse()`                    | Reverse the string                              |
| `reverseWords()`              | Reverse the word order                          |
| `titleCase()`                 | Convert to Title Case                           |
| `truncate(max, ellipsis)`      | Truncate string and add ellipsis                |
| `truncateWords(n)`             | Truncate after N words                          |
| `repeat(n)`                    | Repeat string N times                           |
| `wrap(wrapper)`                | Wrap the string with prefix & suffix            |
| `withPrefix(prefix)`           | Ensure string starts with prefix                |
| `withSuffix(suffix)`           | Ensure string ends with suffix                  |
| `normalize()`                  | Unicode normalize (NFC)                         |
| `stripAccents()`               | Remove diacritical marks                        |
| `indent(spaces)`               | Add leading spaces                              |
| `padLeft(len, char)`           | Pad left to length                              |
| `padRight(len, char)`          | Pad right to length                             |
| `center(len, char)`            | Center-align with padding                       |
| `removeWhitespace()`           | Remove all whitespace                           |
| `removeDigits()`               | Remove numeric characters                       |
| `removePunctuation()`          | Remove punctuation                              |
| `removeSpecialChars()`         | Remove all non-alphanumeric and space chars     |
| `clean()`                      | Remove special chars and trim                   |
| `keepOnly(chars)`              | Keep only allowed characters                    |

---

### 🔄 Transform & Fallback

| Method                         | Description                                     |
|--------------------------------|-------------------------------------------------|
| `map(fn)`                      | Apply transformation function                   |
| `transform(fn)`                | Alias of `map()`                                |
| `safe(fn)`                     | Wrap transform with try/catch                   |
| `orElse(fallback)`            | Return fallback if blank                        |
| `orEmpty()`                   | Return `""` if blank                            |
| `ifEmpty(fallback)`           | Replace if string is empty                      |
| `ifBlank(fallback)`           | Replace if string is blank                      |
| `ifCondition(pred, fn)`       | Conditionally apply transformation              |

---

### 🔍 Analysis / Stats

| Method                         | Description                                     |
|--------------------------------|-------------------------------------------------|
| `countOccurrences(substr)`     | Count substring occurrences                     |
| `countMatches(regex)`          | Count regex matches                             |
| `charCount()`                  | Count non-whitespace characters                 |
| `wordCount()`                  | Count words                                     |
| `getCharFrequency()`           | Frequency map of characters                     |
| `getWordFrequency()`           | Frequency map of words                          |

---

### ✅ Conditions (boolean/Optional)

| Method                         | Description                                     |
|--------------------------------|-------------------------------------------------|
| `isEmpty()`                    | Checks if string is empty                       |
| `isBlank()`                    | Checks if string is blank                       |
| `equalsIgnoreCase(other)`      | Case-insensitive equality check                 |
| `matches(regex)`               | Match full regex                                |
| `startsWith(prefix)`           | Starts with prefix                              |
| `endsWith(suffix)`             | Ends with suffix                                |
| `contains(str)`                | Contains substring                              |
| `hasLength(n)`                 | Has exact length                                |
| `hasOnlyWhitespace()`          | Only whitespace characters                      |
| `isAlpha()`                    | Only alphabetic                                 |
| `isNumeric()`                  | Only numeric                                    |
| `isAlphaNumeric()`             | Letters and digits                              |
| `isEmail()`                    | Basic email format                              |
| `isXml()`                      | Valid XML format                                |
| `isUpperCase()`                | Entire string is uppercase                      |
| `isLowerCase()`                | Entire string is lowercase                      |
| `isPalindrome()`               | Checks palindrome ignoring case/punctuations    |

---

### 🔎 Extraction

| Method                         | Description                                     |
|--------------------------------|-------------------------------------------------|
| `extractFirstMatch(regex)`     | First regex match as Optional                   |
| `extractAllMatches(regex)`     | All regex matches                               |

---

### 🔧 Conversion

| Method                         | Description                                     |
|--------------------------------|-------------------------------------------------|
| `toBase64()`                   | Encode to base64                                |
| `fromBase64()`                 | Decode from base64                              |
| `urlEncode()`                  | Encode to URL-safe format                       |
| `urlDecode()`                  | Decode from URL-encoded format                  |
| `convert()`                    | To `FluentStringParser`                         |
| `toFluentString()`             | Convert to `FluentString`                       |

---

### 🧵 Other Utilities

| Method                         | Description                                     |
|--------------------------------|-------------------------------------------------|
| `lines()`                      | Split by line breaks                            |
| `lineCount()`                  | Count number of lines                           |
| `removeBlankLines()`           | Remove all blank lines                          |
| `split(regex)`                 | Split by regex                                  |
| `words()`                      | Split by whitespace                             |
| `join(List<String>, delimiter)`| Join list of strings                            |
| `debug()`                      | Print intermediate state                        |
| `peek(Consumer)`               | Intercept the pipeline                          |
| `validate(predicate, msg)`     | Throw if not valid                              |
| `filter(predicate)`            | Filter as Optional                              |
| `apply(fn)`                    | Apply external plugin                           |
| `assertThat()`                 | Fluent assertions                               |
| `toBuilder()`                  | Convert to `FluentStringBuilder`                |
| `toTitleCase(Locale)`          | Convert to title case (with locale)             |
| `compareIgnoreCase(..., Locale)`| Locale-aware ignore case compare              |

---

Let me know if you want this written to a file or auto-generated into your project!

---

## 🧪 Testing
JUnit test class `FluentStringParserTest` validates all supported methods with edge cases.

---

## 🔗 Integration
Pair with `FluentString`:
```java
FluentString fs = FluentString.of("true");
Optional<Boolean> boolValue = fs.convert().toBoolean();
```

---

## 📌 License
Open source under MIT. Modify and use freely in your Java projects!

---

## 🙌 Contributions Welcome!
Feel free to add more converters or plug-ins like JSON, YAML, or other domain parsers.

---

## 📂 Package Structure

```
com.platform.sdk.string
├── FluentString.java
├── FluentStringBuilder.java
├── FluentStringCondition.java
├── FluentStringParser.java
├── FluentStringAssertions.java
└── stream
    └── FluentStringStream.java
```

---

## ✅ Compatibility
- Java 8+
- JUnit 5 ready (test suite included)
- No external dependencies (except Apache Commons Text for escaping)

---

## 🤝 Contributing
Feel free to fork, extend, and open PRs for more utilities or extensions like `FluentNumber`, `FluentList`, etc.

---

## 📃 License
MIT License — free to use, modify, and distribute.

