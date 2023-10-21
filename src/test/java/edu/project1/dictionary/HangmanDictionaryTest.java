package edu.project1.dictionary;

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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HangmanDictionaryTest {

    @ParameterizedTest
    @DisplayName("Вернуть случайное слово из списка")
    @MethodSource("getArgumentsForGetRandomWordTest")
    void getRandomWordFromWordList(List<String> wordList) {
        var hangmanDictionary = new HangmanDictionary(wordList);
        String actualResult = hangmanDictionary.getRandomWord();
        assertThat(wordList).contains(actualResult);
    }

    static Stream<Arguments> getArgumentsForGetRandomWordTest() {
        return Stream.of(
            Arguments.of(List.of("car", "bike", "airplane")),
            Arguments.of(List.of("cat", "bird", "bike", "shark")),
            Arguments.of(List.of("developer", "designer", "tester"))
        );
    }

    @ParameterizedTest
    @DisplayName("Получение слова по нужной теме")
    @MethodSource("getArgumentsForGetWordTest")
    void getCorrectThemeWord(Theme theme) throws IOException {
        Path path = Path.of("src", "main", "resources", "dictionary", "%s.txt".formatted(theme.name().toLowerCase()));
        var listOfExpectedWords = Files.readAllLines(path);
        var hangmanDictionary = new HangmanDictionary(theme);
        var actualResult = hangmanDictionary.getRandomWord();
        Assertions.assertThat(listOfExpectedWords).contains(actualResult);
    }

    static Stream<Arguments> getArgumentsForGetWordTest() {
        return Stream.of(
            Arguments.of(Theme.ANIMALS),
            Arguments.of(Theme.FOOD),
            Arguments.of(Theme.JOBS),
            Arguments.of(Theme.HOME)
        );
    }

    @Test
    @DisplayName("Выброс исключения при пустом файле")
    void shouldThrowDictionaryExceptionIfResourceFileIsEmpty() {
        var exception = assertThrows(DictionaryException.class, () -> new HangmanDictionary(Theme.COUNTRIES));
        assertThat(exception.getMessage()).isEqualTo(
            "Could not initialize dictionaries: File is empty - countries.txt");
    }

    @Test
    @DisplayName("Выброс исключения при несуществующем файле")
    void shouldThrowDictionaryExceptionIfResourceFileDoesNotExist() {
        var exception = assertThrows(DictionaryException.class, () -> new HangmanDictionary(Theme.CLOTHES));
        assertThat(exception.getCause()).isInstanceOf(IOException.class);
        assertThat(exception.getMessage()).isEqualTo(
            "Could not initialize dictionaries: " + exception.getCause().getMessage());
    }
}
