package com.impayonline.models.templates;

import com.impayonline.models.words.Symbols;
import lombok.ToString;

import java.util.Arrays;
import java.util.regex.Pattern;

@ToString
public class LiteralPatterns {
    private static final String prefix = "^.*";
    private static final String suffix = ".*$";

    public static Pattern fromTemplate(String template) {
        final String strippedTemplate = stripBeginAndEndDelimiters(template);
        final String regexifiedTemplate = regexifyTemplate(strippedTemplate);
        return Pattern.compile(regexifiedTemplate);
    }

    private static String regexifyTemplate(CharSequence template) {
        final String[] rawSymbols = Symbols.splitToSymbols(template);
        final String[] regexifiedSymbols = Arrays.stream(rawSymbols).map(Symbols::regexify).toArray(String[]::new);
        final String symbolsRegex = Symbols.concatenateToString(regexifiedSymbols);
        return prefix + symbolsRegex + suffix;
    }

    private static String stripBeginAndEndDelimiters(String template) {
        return template.substring(1, template.length() - 1);
    }
}
