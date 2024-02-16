package com.impayonline.io;

import com.impayonline.models.templates.Template;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Prettifier {
    public static String[] prettifyOutput(Map<Template, Integer> matchesCount) {
        final int longestTemplateLength = calculateLengthOfLongestTemplate(matchesCount.keySet());
        final String[] prettyLines = new String[matchesCount.size()];
        for (Map.Entry<Template, Integer> entry : matchesCount.entrySet())
            prettyLines[entry.getKey().lineNumber] = prettifyLine(entry, longestTemplateLength);
        return prettyLines;
    }

    private static String prettifyLine(Map.Entry<Template, Integer> entry, int maxTemplateLength) {
        return entry.getKey().prettyForm
                + calculatePaddingFromTemplateToCount(entry.getKey().prettyForm.length(), maxTemplateLength)
                + entry.getValue();
    }

    private static String calculatePaddingFromTemplateToCount(int templateLength, int maxTemplateLength) {
        return " ".repeat(maxTemplateLength - templateLength + 4);
    }

    private static int calculateLengthOfLongestTemplate(Iterable<Template> templates) {
        int maxLength = 0;

        for (Template template : templates)
            if (template.prettyForm.length() > maxLength)
                maxLength = template.prettyForm.length();

        return maxLength;
    }
}
