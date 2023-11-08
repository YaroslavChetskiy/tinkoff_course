package edu.hw5.task4;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw5.task4.Task4.validatePassword;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task4Test {

    @ParameterizedTest
    @DisplayName("Проверка на валидность пароля")
    @MethodSource("getArgumentsForPasswordValidationTest")
    void checkPasswordValidation(String password, boolean expectedResult) {
        var actualResult = validatePassword(password);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForPasswordValidationTest() {
        return Stream.of(
            Arguments.of("lkdf~kfdl", true),
            Arguments.of("!adbc", true),
            Arguments.of("dkfdl@", true),
            Arguments.of("#te!st@Al#1l$Sy^m2bo&3ls*|", true),
            Arguments.of("########", true),
            Arguments.of("", false),
            Arguments.of(null, false),
            Arguments.of("abc", false),
            Arguments.of("39329", false),
            Arguments.of("--.--.--.--", false)
        );
    }



}
