package edu.project1.dictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

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
}
