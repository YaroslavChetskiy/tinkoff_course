package edu.hw3.task1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw3.task1.Task1.atbashCipher;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {

    @ParameterizedTest
    @DisplayName("Использование шифра Атбаша")
    @MethodSource("getArgumentsForAtbashCipherTest")
    void checkAtbashCipher(String inputString, String expectedResult) {
        var actualResult = atbashCipher(inputString);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForAtbashCipherTest() {
        return Stream.of(
            Arguments.of("", ""),
            Arguments.of("abc", "zyx"),
            Arguments.of("Hello world!", "Svool dliow!"),
            Arguments.of(null, null),
            Arguments.of("4sjds38ds-jA", "4hqwh38wh-qZ"),
            Arguments.of("привет", "привет")
        );
    }

}
