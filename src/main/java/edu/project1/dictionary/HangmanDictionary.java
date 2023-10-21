package edu.project1.dictionary;

import edu.project1.exception.DictionaryException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class HangmanDictionary implements Dictionary {
    private static final Random RANDOM = new Random();

    private final List<String> wordList;

    public HangmanDictionary(Theme theme) {
        Path resourcePath =
            Path.of("src", "main", "resources", "dictionary", "%s.txt".formatted(theme.name().toLowerCase()));
        try {
            wordList = Files.readAllLines(resourcePath);
            if (wordList.size() == 0) {
                throw new DictionaryException(
                    "Could not initialize dictionaries: File is empty - " + resourcePath.getFileName());
            }
        } catch (IOException exception) {
            throw new DictionaryException(
                "Could not initialize dictionaries: " + exception.getMessage(),
                exception
            );
        }
    }

    public HangmanDictionary(List<String> wordList) {
        this.wordList = wordList;
    }

    @Override
    public @NotNull String getRandomWord() {
        return wordList.get(RANDOM.nextInt(0, wordList.size()));
    }
}
