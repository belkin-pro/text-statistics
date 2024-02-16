package com.impayonline.models.templates;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@ToString
public class ConstraintPatterns {
    private static final String prefix = "^";
    private static final String suffix = ".*$";
    private static final String template = "(?=(.*%s){%s})";

    public static Pattern fromTemplate(CharSequence template) {
        final Map<String, String> constraints = parseAsMap(template);
        return compile(constraints);
    }

    private static Map<String, String> parseAsMap(CharSequence template) {
        final int templateLength = template.length();
        final Map<String, String> constraints = new HashMap<>(templateLength / 2);
        for (int i = 0; i < templateLength; i += 2)
            constraints.put(String.valueOf(template.charAt(i)), String.valueOf(template.charAt(i + 1)));
        return constraints;
    }

    private static Pattern compile(Map<String, String> constraints) {
        final StringBuilder builder = createBuilder(constraints.size());
        builder.append(prefix);
        builder.append(constructRegexString(constraints));
        builder.append(suffix);
        return Pattern.compile(builder.toString());
    }

    private static CharSequence constructRegexString(Map<String, String> constraints) {
        final int nbConstraints = constraints.size();
        final StringBuilder regex = new StringBuilder(template.length() * nbConstraints);
        for (Map.Entry<String, String> entry : constraints.entrySet())
            regex.append(String.format(template, entry.getKey(), entry.getValue()));
        return regex.toString();
    }

    private static StringBuilder createBuilder(int nbConstraints) {
        return new StringBuilder(prefix.length() + suffix.length() + template.length() * nbConstraints);
    }
}
