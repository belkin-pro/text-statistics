package com.impayonline.logic;

import com.impayonline.models.*;
import com.impayonline.models.templates.Template;
import com.impayonline.models.words.Word;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatisticsCalculator {
    public static Map<Template, Integer> countWordsMatchingTemplates(
            Iterable<? extends Model> templates,
            Iterable<? extends Model> words
    ) {
        final Map<Template, Integer> matchesCount = new TreeMap<>();
        int count;
        for (Model templateModel : templates) {
            final Template template = (Template) templateModel;
            count = countMatches(template, words);
            matchesCount.put(template, count);
        }

        return matchesCount;
    }

    private static int countMatches(Template template, Iterable<? extends Model> words) {
        int count = 0;
        for (Model wordModel : words) {
            final Matcher matcher = template.pattern.matcher(((Word) wordModel).raw());
            if (matcher.matches())
                count++;
        }
        return count;
    }
}
