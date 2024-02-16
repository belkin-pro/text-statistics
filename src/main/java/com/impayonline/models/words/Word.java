package com.impayonline.models.words;

import com.impayonline.io.Parser;
import com.impayonline.models.Model;
import lombok.ToString;

import java.io.*;
import java.util.*;

@ToString
public final class Word extends Model {
    private final String word;

    private Word(String word) {
        if (isWhitespaceInWord(word))
            throw new WordContainsWhitespaceException();
        this.word = word;
    }

    public static List<Word> parseFromRawReader(BufferedReader reader) {
        try {
            final List<Word> words = new ArrayList<>(20);
            String line = reader.readLine();
            while (line != null) {
                words.addAll(parseRawLineIntoSingleWords(line));
                line = reader.readLine();
            }
            return words;
        } catch (IOException e) {
            throw new Parser.FindingOpeningOrReadingFileException(e.getMessage());
        }
    }

    public String raw() {
        return word;
    }

    private static boolean isWhitespaceInWord(String word) {
        boolean hasWhitespace = false;
        final Character[] whitespaceChars = {' ', '\n', '\r', '\t'};
        for (char c : word.toCharArray())
            if (Arrays.stream(whitespaceChars).anyMatch(whitespace -> c == whitespace))
                hasWhitespace = true;
        return hasWhitespace;
    }

    private static Collection<Word> parseRawLineIntoSingleWords(String line) {
        final Collection<Word> words = new ArrayList<>(10);
        final String[] rawWords = line.split(" ");
        for (String rawWord : rawWords)
            words.add(new Word(rawWord));
        return words;
    }

    private static class WordContainsWhitespaceException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 5678077517855505088L;
    }
}
