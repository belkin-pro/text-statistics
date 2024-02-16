package com.impayonline;

import com.impayonline.models.templates.Template;
import com.impayonline.models.words.Word;
import com.impayonline.io.Parser;
import com.impayonline.logic.StatisticsCalculator;
import com.impayonline.io.Writer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class App {
    public static void main(String[] args) {
        final Parser<Word> wordParser = new Parser<>(Word::parseFromRawReader);
        final List<Word> words = wordParser.readFromFile(args[0]);

        final Parser<Template> templateParser = new Parser<>(Template::parseFromRawReader);
        final List<Template> templates = templateParser.readFromFile(args[1]);

        final Map<Template, Integer> matchesCount = StatisticsCalculator.countWordsMatchingTemplates(templates, words);

        Writer.writeTemplatesWithMatchesCountToFile(matchesCount, args[2]);
    }
}
