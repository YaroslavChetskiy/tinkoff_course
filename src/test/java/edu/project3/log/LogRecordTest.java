package edu.project3.log;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class LogRecordTest {

    @ParameterizedTest
    @DisplayName("Проверка, находится ли в лог в нужном временном промежутке")
    @MethodSource("getArgumentsForIsBetweenDatesTest")
    void isBetweenDates(LogRecord logRecord, LocalDate from, LocalDate to, boolean expectedResult) {
        var actualResult = logRecord.isBetweenDates(from, to);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForIsBetweenDatesTest() {
        return Stream.of(
            Arguments.of(
                new LogRecord(
                    "dummy",
                    "dummy",
                    OffsetDateTime.of(2015, 3, 2, 0, 0, 0, 0, ZoneOffset.MIN),
                    new RequestRecord("dummy", "dummy", "dummy"),
                    0
                    , 0L,
                    "dummy",
                    "dummy"
                ),
                LocalDate.of(1999, 2, 3),
                LocalDate.of(2024, 4, 5),
                true
            ),
            Arguments.of(
                new LogRecord(
                    "dummy",
                    "dummy",
                    OffsetDateTime.of(1999, 5, 2, 1, 2, 3, 4, ZoneOffset.MIN),
                    new RequestRecord("dummy", "dummy", "dummy"),
                    0
                    , 0L,
                    "dummy",
                    "dummy"
                ),
                LocalDate.of(1999, 5, 1),
                LocalDate.of(1999, 5, 2),
                true
            ),
            Arguments.of(
                new LogRecord(
                    "dummy",
                    "dummy",
                    OffsetDateTime.of(1999, 5, 2, 1, 2, 3, 4, ZoneOffset.MIN),
                    new RequestRecord("dummy", "dummy", "dummy"),
                    0
                    , 0L,
                    "dummy",
                    "dummy"
                ),
                LocalDate.of(2000, 5, 1),
                LocalDate.of(2023, 5, 2),
                false
            ),
            Arguments.of(
                new LogRecord(
                    "dummy",
                    "dummy",
                    OffsetDateTime.of(1999, 5, 2, 1, 2, 3, 4, ZoneOffset.MIN),
                    new RequestRecord("dummy", "dummy", "dummy"),
                    0
                    , 0L,
                    "dummy",
                    "dummy"
                ),
                LocalDate.of(1988, 5, 1),
                LocalDate.of(1998, 5, 2),
                false
            )
        );
    }
}
