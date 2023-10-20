package edu.project1.utils;

import edu.project1.dictionary.Theme;
import edu.project1.exception.DictionaryException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Как я понимаю, тестирование утилитарных классов происходит с помощью
// PowerMock или Reflection API, но оба этих способа
// являются не лучшей практикой, как я слышал
public final class ThemesWordListUtil {

    private static final Map<Theme, List<String>> WORDS_MAP = new HashMap<>();

    private ThemesWordListUtil() {
    }

    static {
        initWordsMap();
    }

    public static List<String> getWordList(Theme theme) {
        return WORDS_MAP.get(theme);
    }

    private static void initWordsMap() {
        for (Theme theme : Theme.values()) {
            Path resourcePath =
                Path.of("src", "main", "resources", "dictionary", "%s.txt".formatted(theme.name().toLowerCase()));
            try {
                List<String> wordsList = Files.readAllLines(resourcePath);
                if (wordsList.size() == 0) {
                    throw new DictionaryException(
                        "Could not initialize dictionaries: File is empty - " + resourcePath.getFileName());
                }
                WORDS_MAP.put(theme, wordsList);
            } catch (IOException exception) {
                throw new DictionaryException(
                    "Could not initialize dictionaries: " + exception.getMessage(),
                    exception
                );
            }
        }
    }

}
