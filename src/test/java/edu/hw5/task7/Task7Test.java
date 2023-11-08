package edu.hw5.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw5.task7.Task7.containsAtLeastThreeCharactersWithThirdZero;
import static edu.hw5.task7.Task7.lengthNoLessThanOneAndNoMoreThanThree;
import static edu.hw5.task7.Task7.startsWithEndCharacter;
import static org.assertj.core.api.Assertions.assertThat;

class Task7Test {

    @ParameterizedTest
    @DisplayName("Проверка, содержит ли строка из 0 и 1 минимум 3 символа и третий символ 0")
    @MethodSource("getArgumentsForAtLeastThreeCharactersWithThirdZeroTest")
    void checkContainsAtLeastThreeCharactersWithThirdZero(String string, boolean expectedResult) {
        var actualResult = containsAtLeastThreeCharactersWithThirdZero(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForAtLeastThreeCharactersWithThirdZeroTest() {
        return Stream.of(
            Arguments.of("100", true),
            Arguments.of("000", true),
            Arguments.of("11011", true),
            Arguments.of("1", false),
            Arguments.of("10", false),
            Arguments.of("12011", false),
            Arguments.of("101", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Проверка, начинается ли строка из 0 и 1 на тот же символ, которым заканчивается")
    @MethodSource("getArgumentsForStartsWithEndCharacterTest")
    void checkStartsWithEndCharacter(String string, boolean expectedResult) {
        var actualResult = startsWithEndCharacter(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForStartsWithEndCharacterTest() {
        return Stream.of(
            Arguments.of("101", true),
            Arguments.of("1", true),
            Arguments.of("0", true),
            Arguments.of("0110", true),
            Arguments.of("00000", true),
            Arguments.of("10", false),
            Arguments.of("202", false),
            Arguments.of("100", false),
            Arguments.of("0111", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Проверка, имеет ли строка из 0 и 1 длину не меньше 1 и не больше 3")
    @MethodSource("getArgumentsForLengthNoLessThanOneAndNoMoreThanThreeTest")
    void checkLengthNoLessThanOneAndNoMoreThanThree(String string, boolean expectedResult) {
        var actualResult = lengthNoLessThanOneAndNoMoreThanThree(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForLengthNoLessThanOneAndNoMoreThanThreeTest() {
        return Stream.of(
            Arguments.of("101", true),
            Arguments.of("000", true),
            Arguments.of("111", true),
            Arguments.of("0", true),
            Arguments.of("10", true),
            Arguments.of("99", false),
            Arguments.of("1111", false),
            Arguments.of("01010101001", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

}
