package com.platform.sdk.string;

/**
 * Interface for extending FluentString behavior via plugin-style hooks.
 */
@FunctionalInterface
public interface FluentStringPlugin {
    String apply(String input);
}
