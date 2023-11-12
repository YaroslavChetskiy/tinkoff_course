package edu.hw5.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw5.task5.Task5.validateRussianCarNumber;
import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {

    @ParameterizedTest
    @DisplayName("Проверка на валидность российского номерного знака")
    @MethodSource("getArgumentsForRussianCarNumberValidationTest")
    void checkRussianCarNumberValidation(String number, boolean expectedResult) {
        var actualResult = validateRussianCarNumber(number);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForRussianCarNumberValidationTest() {
        return Stream.of(
            Arguments.of("А123ВЕ777", true),
            Arguments.of("О777ОО177", true),
            Arguments.of("К321УХ51", true),
            Arguments.of("", false),
            Arguments.of(null, false),
            Arguments.of("123АВЕ777", false),
            Arguments.of("A123ВГ77", false),
            Arguments.of("А123ВЕ7777", false),
            Arguments.of("A123BE777", false) // latin symbols
        );
    }

}
