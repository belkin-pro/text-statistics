package com.impayonline.io;

import lombok.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@AllArgsConstructor
@ToString
public class Parser<T> {
    private final Function<BufferedReader, List<T>> parseRawInputFromReaderAsEntities;

    public List<T> readFromFile(String filename) {
        final File file = new File(filename);
        return parseFile(file);
    }

    private List<T> parseFile(File file) {
        try {
            final BufferedReader reader = createBufferedFileReader(file);
            final List<T> entities = parseRawInputFromReaderAsEntities.apply(reader);
            reader.close();
            return entities;
        } catch (IOException e) {
            throw new FindingOpeningOrReadingFileException(e.getMessage());
        }
    }

    private static BufferedReader createBufferedFileReader(File file) throws IOException {
        return new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
    }

    @AllArgsConstructor
    @ToString
    public static class FindingOpeningOrReadingFileException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1026981011827205998L;
        public final String message;
    }
}
