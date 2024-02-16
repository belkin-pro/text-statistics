package com.impayonline.models.templates;

import com.impayonline.io.Parser;
import com.impayonline.models.Model;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class Template extends Model implements Comparable<Template> {
    public final String prettyForm;
    public final Pattern pattern;
    public final int lineNumber;

    private Template(String prettyForm, int lineNumber) {
        this.prettyForm = prettyForm;
        pattern = convertToPattern(prettyForm);
        this.lineNumber = lineNumber;
    }

    public static List<Template> parseFromRawReader(BufferedReader reader) {
        try {
            final List<Template> templates = new ArrayList<>(20);
            String line = reader.readLine();
            int lineNumber = 0;
            while (line != null) {
                templates.addAll(parseRawLineIntoTemplates(line, lineNumber));
                lineNumber++;
                line = reader.readLine();
            }
            return templates;
        } catch (IOException e) {
            throw new Parser.FindingOpeningOrReadingFileException(e.getMessage());
        }
    }

    private static Collection<Template> parseRawLineIntoTemplates(String line, int lineNumber) {
        return List.of(new Template(line, lineNumber));
    }

    private static Pattern convertToPattern(String template) {
        final Pattern pattern;

        if (isLiteralTemplate(template))
            pattern = LiteralPatterns.fromTemplate(template);
        else if (isConstraintTemplate(template))
            pattern = ConstraintPatterns.fromTemplate(template);
        else
            throw new InvalidTemplateException();

        return pattern;
    }

    private static boolean isLiteralTemplate(String template) {
        return template.startsWith("\"") && template.endsWith("\"") && template.length() > 1;
    }

    private static boolean isConstraintTemplate(CharSequence template) {
        final Pattern pattern = Pattern.compile("(?<letterAndDigit>[a-zA-Zа-яА-ЯёЁ0-9]\\d)+");
        final Matcher matcher = pattern.matcher(template);
        return matcher.matches();
    }

    @Override
    public int compareTo(Template otherTemplate) {
        return Integer.compare(lineNumber, otherTemplate.lineNumber);
    }

    private static class InvalidTemplateException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1595691066848028752L;
    }
}
