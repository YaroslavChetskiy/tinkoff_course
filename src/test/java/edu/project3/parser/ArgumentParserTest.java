package edu.project3.parser;

import edu.project3.config.Config;
import edu.project3.exception.ArgumentsParseException;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project3.statistics.StatsOutputFormat.ADOC;
import static edu.project3.statistics.StatsOutputFormat.MARKDOWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgumentParserTest {

    @ParameterizedTest
    @DisplayName("Парсинг корректных аргументов")
    @MethodSource("getArgumentsForGetCorrectParsedArgsTest")
    void getCorrectParsedArgs(String[] args, Config expectedResult) {
        var actualResult = ArgumentParser.parse(args);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetCorrectParsedArgsTest() {
        return Stream.of(
            Arguments.of(
                new String[] {
                    "--path",
                    "src/correct_log.txt",
                    "--from",
                    "2023-01-01",
                    "--to",
                    "2023-01-02",
                    "--format",
                    "adoc"
                },
                new Config(
                    "src/correct_log.txt",
                    LocalDate.of(2023, 1, 1),
                    LocalDate.of(2023, 1, 2),
                    ADOC
                )
            ),
            Arguments.of(
                new String[] {
                    "--path",
                    "src/correct_log.txt",
                    "--from",
                    "2023-01-01",
                    "--format",
                    "adoc"
                },
                new Config(
                    "src/correct_log.txt",
                    LocalDate.of(2023, 1, 1),
                    LocalDate.MAX,
                    ADOC
                )
            ),
            Arguments.of(
                new String[] {
                    "--path",
                    "src/correct_log.txt",
                    "--format",
                    "markdown"
                },
                new Config(
                    "src/correct_log.txt",
                    LocalDate.MIN,
                    LocalDate.MAX,
                    MARKDOWN
                )
            ),
            Arguments.of(
                new String[] {
                    "--path",
                    "src/correct_log.txt"
                },
                new Config(
                    "src/correct_log.txt",
                    LocalDate.MIN,
                    LocalDate.MAX,
                    ADOC
                )
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при некорректных данных")
    @MethodSource("getArgumentsForThrowExceptionTest")
    void throwArgumentsParseExceptionIfArgsAreNotValid(String[] args) {
        assertThrows(ArgumentsParseException.class, () -> ArgumentParser.parse(args));
    }

    static Stream<Arguments> getArgumentsForThrowExceptionTest() {
        return Stream.of(
            Arguments.of(
                (Object) new String[] {
                    "src/correct_log.txt",
                    "--from",
                    "2023-01-01",
                    "--to",
                    "2023-01-02",
                    "--format",
                    "adoc"
                }
            ),
            Arguments.of(
                (Object) new String[] {
                    "--path",
                    "src/correct_log.txt",
                    "--from",
                    "2023-99-01",
                    "--format",
                    "adoc"
                }
            ),
            Arguments.of(
                (Object) new String[] {
                    "--path",
                    "src/correct_log.txt",
                    "--format",
                    "dummy"
                }
            ),
            Arguments.of(
                (Object) new String[] {
                    "--path",
                    ""
                }
            ),
            Arguments.of(
                (Object) new String[0]
            ),
            Arguments.of(
                (Object) new String[] {
                    "--path",
                    "--format",
                    "markdown"
                }
            )
        );
    }

}
