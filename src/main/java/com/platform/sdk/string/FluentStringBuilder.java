package com.platform.sdk.string;

public class FluentStringBuilder {

    private final StringBuilder builder;

    private FluentStringBuilder() {
        this.builder = new StringBuilder();
    }

    public static FluentStringBuilder start() {
        return new FluentStringBuilder();
    }

    public FluentStringBuilder append(String str) {
        builder.append(str);
        return this;
    }

    public FluentStringBuilder space() {
        builder.append(' ');
        return this;
    }

    public FluentStringBuilder comma() {
        builder.append(',');
        return this;
    }

    public FluentStringBuilder dot() {
        builder.append('.');
        return this;
    }

    public FluentStringBuilder exclaim() {
        builder.append('!');
        return this;
    }

    public FluentStringBuilder newline() {
        builder.append(System.lineSeparator());
        return this;
    }

    public FluentStringBuilder tab() {
        builder.append('\t');
        return this;
    }

    public FluentStringBuilder clear() {
        builder.setLength(0);
        return this;
    }

    public int length() {
        return builder.length();
    }

    public boolean isEmpty() {
        return builder.length() == 0;
    }

    public String build() {
        return builder.toString();
    }

    @Override
    public String toString() {
        return build();
    }

    public FluentString toFluentString() {
        return FluentString.of(this.build());
    }
}