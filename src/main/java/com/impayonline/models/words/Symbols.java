package com.impayonline.models.words;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Symbols {
    public static String regexify(String symbol) {
        String regexified = symbol;
        if (isEscapable(symbol))
            regexified = escape(regexified);
        regexified = wrapInSquareBraces(regexified);
        return regexified;
    }

    public static String concatenateToString(String... symbols) {
        final StringBuilder builder = new StringBuilder(symbols.length);
        for (String symbol : symbols)
            builder.append(symbol);
        return builder.toString();
    }

    public static String[] splitToSymbols(CharSequence template) {
        return Pattern.compile("").split(template);
    }

    private static String wrapInSquareBraces(String symbol) {
        return "[" + symbol + "]";
    }

    private static String escape(String symbol) {
        return "\\" + symbol;
    }

    private static boolean isEscapable(String symbol) {
        final List<String> escapableSymbols = List.of(
                "<", "(", "[", "{", "\\", "^", "-", "=", "$", "!", "|", "]", "}", ")", "?", "*", "+", ".", ">"
        );
        return escapableSymbols.contains(symbol);
    }
}
