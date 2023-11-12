package edu.hw5.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw5.task2.Task2.getAllFriday13thOfYear;
import static edu.hw5.task2.Task2.getNextFriday13th;
import static org.assertj.core.api.Assertions.assertThat;

class Task2Test {

    @ParameterizedTest
    @DisplayName("Получение следующей пятницы 13")
    @MethodSource("getArgumentsForNextFriday13thTest")
    void checkGetNextFriday13th(LocalDate date, LocalDate expectedResult) {
        var actualResult = getNextFriday13th(date);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForNextFriday13thTest() {
        return Stream.of(
            Arguments.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 9, 13)
            ),
            Arguments.of(
                LocalDate.of(1925, 4, 12),
                LocalDate.of(1925, 11, 13)
            ),
            Arguments.of(
                LocalDate.of(1925, 1, 1),
                LocalDate.of(1925, 2, 13)
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение всех пятниц 13-х в году")
    @MethodSource("getArgumentsForGetAllFriday13thOfYearTest")
    void checkGetAllFriday13thOfYear(int year, List<LocalDate> expectedResult) {
        var actualResult = getAllFriday13thOfYear(year);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetAllFriday13thOfYearTest() {
        return Stream.of(
            Arguments.of(
                1925,
                List.of(
                    LocalDate.of(1925, 2, 13),
                    LocalDate.of(1925, 3, 13),
                    LocalDate.of(1925, 11, 13)
                )
            ),
            Arguments.of(
                2024,
                List.of(
                    LocalDate.of(2024, 9, 13),
                    LocalDate.of(2024, 12, 13)
                )
            )
        );
    }
}
