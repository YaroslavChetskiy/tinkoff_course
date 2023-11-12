package edu.hw5.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw5.task6.Task6.isSubsequence;
import static org.assertj.core.api.Assertions.assertThat;

class Task6Test {

    @ParameterizedTest
    @DisplayName("Проверка, является ли строка подпоследовательностью другой")
    @MethodSource("getArgumentsForIsSubsequenceTest")
    void checkIsSubsequence(String seq, String string, boolean expectedResult) {
        var actualResult = isSubsequence(seq, string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForIsSubsequenceTest() {
        return Stream.of(
            Arguments.of("abc", "achfdbaabgabcaabg", true),
            Arguments.of("ac", "acacac", true),
            Arguments.of("kce", "bkfdffce", true),
            Arguments.of("ttt", "t---t---t", true),
            Arguments.of("e", "abcdefgh", true),
            Arguments.of("", "abc", true),
            Arguments.of("", "", true),
            Arguments.of("abc", "acbed", false),
            Arguments.of("bc", "acbed", false),
            Arguments.of("abc", "", false),
            Arguments.of("111", null, false),
            Arguments.of(null, "cde", false)
        );
    }

}
