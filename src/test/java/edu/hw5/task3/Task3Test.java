package edu.hw5.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static edu.hw5.task3.Task3.parseDate;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {

    @ParameterizedTest
    @DisplayName("Получение даты из строки, используя цепочку парсеров")
    @MethodSource("getArgumentsForGetParsedDateTest")
    void getParsedDate(String date, Optional<LocalDate> expectedResult) {
        var actualResult = parseDate(date);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetParsedDateTest() {
        return Stream.of(
            Arguments.of(
                "1965-09-10",
                Optional.of(LocalDate.of(1965, 9, 10))
            ),
            Arguments.of(
                "2020-12-2",
                Optional.of(LocalDate.of(2020, 12, 2))
            ),
            Arguments.of(
                "1/3/1976",
                Optional.of(LocalDate.of(1976, 3, 1))
            ),
            Arguments.of(
                "1/3/20",
                Optional.of(LocalDate.of(2020, 3, 1))
            ),
            Arguments.of(
                "2/5/76",
                Optional.of(LocalDate.of(1976, 5, 2))
            ),
            Arguments.of(
                "tomorrow",
                Optional.of(LocalDate.now().plusDays(1L))
            ),
            Arguments.of(
                "today",
                Optional.of(LocalDate.now())
            ),
            Arguments.of(
                "yesterday",
                Optional.of(LocalDate.now().minusDays(1L))
            ),
            Arguments.of(
                "1 month ago",
                Optional.of(LocalDate.now().minusMonths(1L))
            ),
            Arguments.of(
                "2 days ago",
                Optional.of(LocalDate.now().minusDays(2L))
            ),
            Arguments.of(
                "5 years after",
                Optional.of(LocalDate.now().plusYears(5L))
            ),
            Arguments.of(
                "1 week after",
                Optional.of(LocalDate.now().plusWeeks(1L))
            ),
            Arguments.of(
                "2020.11.01",
                Optional.empty()
            ),
            Arguments.of(
                null,
                Optional.empty()
            )
        );
    }
}
