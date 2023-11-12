package edu.hw5.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw5.task1.Task1.getAverageSessionTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    @ParameterizedTest
    @DisplayName("Вычисление среднего времени сеансов")
    @MethodSource("getArgumentsForGetCorrectAverageSessionTimeTest")
    void getCorrectAverageSessionTime(List<String> sessionsTime, Duration expectedResult) {
        var actualResult = getAverageSessionTime(sessionsTime);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetCorrectAverageSessionTimeTest() {
        return Stream.of(
            Arguments.of(
                List.of(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                Duration.parse("PT3H40M")
            ),
            Arguments.of(
                List.of(
                    "2022-03-12, 18:20 - 2022-03-13, 03:50", // 9 h 30 m
                    "2022-04-01, 21:30 - 2022-04-01, 21:45", // 15 m
                    "2022-04-03, 03:30 - 2022-04-04, 00:00" // 20 h 30 m
                ),
                Duration.parse("PT10H5M")
            ),
            Arguments.of(
                List.of(
                    "2022-04-12, 20:20 - 2022-04-12, 20:20"
                ),
                Duration.ZERO
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неправильных вводимых данных")
    @MethodSource("getArgumentsForInvalidSessionsTimeTest")
    void throwIllegalArgumentExceptionIfSessionsTimeIsInvalid(List<String> sessionsTime) {
        var exception =
            assertThrows(IllegalArgumentException.class, () -> getAverageSessionTime(sessionsTime));
        assertThat(exception.getMessage()).isEqualTo("Invalid sessions time");
    }

    static Stream<Arguments> getArgumentsForInvalidSessionsTimeTest() {
        return Stream.of(
            Arguments.of(List.of()),
            Arguments.of((Object) null),
            Arguments.of(
                List.of(
                    "20:20, 2022-04-12 - 20:20, 2022-04-12"
                )
            ),
            Arguments.of(
                List.of(
                    "2022-03-12, 18:20 - 2022-03-13, 03:50",
                    "2022.04.01, 21:30 - 2022.04.01, 21:45",
                    "2022-04-03 - 2022-04-04"
                    )
            )
        );
    }

}
