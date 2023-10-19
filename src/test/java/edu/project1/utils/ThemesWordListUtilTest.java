package edu.project1.utils;

import edu.project1.dictionary.Theme;
import edu.project1.exception.DictionaryException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import static edu.project1.utils.ThemesWordListUtil.getWordList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ThemesWordListUtilTest {

    @ParameterizedTest
    @DisplayName("Получение списка слов по нужной теме")
    @MethodSource("getArgumentsForGetWordsListTest")
    void getCorrectThemeWordsList(Theme theme) throws IOException {
        Path path = Path.of("src", "main", "resources", "dictionary", "%s.txt".formatted(theme.name().toLowerCase()));
        var expectedResult = Files.readAllLines(path);
        var actualResult = getWordList(theme);
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetWordsListTest() {
        return Stream.of(
            Arguments.of(Theme.ANIMALS),
            Arguments.of(Theme.FOOD),
            Arguments.of(Theme.JOBS),
            Arguments.of(Theme.HOME)
        );
    }
}
