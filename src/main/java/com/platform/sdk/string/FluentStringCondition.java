package com.platform.sdk.string;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Supports chained conditional transformations on a FluentString.
 */
public class FluentStringCondition {

    private final FluentString original;
    private final boolean conditionMet;
    private final FluentString result;

    private FluentStringCondition(FluentString original, boolean conditionMet, FluentString result) {
        this.original = original;
        this.conditionMet = conditionMet;
        this.result = result;
    }

    public static FluentStringCondition ifTrue(FluentString input, Predicate<String> condition, Function<String, String> transform) {
        boolean match = condition.test(input.result());
        FluentString transformed = match
                ? FluentString.of(transform.apply(input.result()))
                : input;

        return new FluentStringCondition(input, match, transformed);
    }

    public FluentString elseTransform(Function<String, String> fallbackTransform) {
        if (conditionMet) {
            return result;
        } else {
            return FluentString.of(fallbackTransform.apply(original.result()));
        }
    }
}
