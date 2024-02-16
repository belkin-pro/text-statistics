package com.impayonline.io;

import com.impayonline.models.templates.Template;
import lombok.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Writer {
    public static void writeTemplatesWithMatchesCountToFile(Map<Template, Integer> matchesCount, String filename) {
        try {
            final FileWriter outputWriter = new FileWriter(filename, StandardCharsets.UTF_8);
            final String[] prettyLines = Prettifier.prettifyOutput(matchesCount);
            outputLinesToFile(prettyLines, outputWriter);
            outputWriter.close();
        } catch (IOException e) {
            throw new OutputStreamIOException(e.getMessage());
        }
    }

    private static void outputLinesToFile(String[] lines, FileWriter fileWriter) throws IOException {
        final BufferedWriter writer = new BufferedWriter(fileWriter);
        for (String line : lines)
            writer.append(line).append(System.lineSeparator());
        writer.close();
    }

    @AllArgsConstructor
    @ToString
    private static class OutputStreamIOException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -8135856888555331068L;
        public final String message;
    }
}
