package edu.project1.dictionary;

import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class HangmanDictionary implements Dictionary  {
    private static final Random RANDOM = new Random();

    private final List<String> wordList;

    public HangmanDictionary(List<String> wordList) {
        this.wordList = wordList;
    }

    @Override
    public @NotNull String getRandomWord() {
        return wordList.get(RANDOM.nextInt(0, wordList.size()));
    }
}
