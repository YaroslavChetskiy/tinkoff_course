package edu.project1.console;

import edu.project1.guessResult.GuessResult;
import edu.project1.session.Session;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class ConsoleHangmanTest {

    private final Session session = new Session("dummy", 6);
    private final ConsoleHangman consoleHangman = new ConsoleHangman();

    @ParameterizedTest
    @DisplayName("Проверка попытки угадывания")
    @MethodSource("getArgumentsForTryExecuteTest")
    void checkTryGuess(String input, Class<? extends GuessResult> expectedResult) {
        var actualResult = consoleHangman.tryGuess(session, input);
        assertThat(actualResult).isInstanceOf(expectedResult);
    }

    static Stream<Arguments> getArgumentsForTryExecuteTest() {
        return Stream.of(
            Arguments.of(
                "123232", GuessResult.Retry.class
            ),
            Arguments.of(
                "jsjdks", GuessResult.Retry.class
            ),
            Arguments.of(
                "", GuessResult.Retry.class
            ),
            Arguments.of(
                "-", GuessResult.Retry.class
            ),
            Arguments.of(
                "0", GuessResult.Retry.class
            ),
            Arguments.of(
                ".", GuessResult.Retry.class
            ),
            Arguments.of(
                "/quit", GuessResult.Defeat.class
            ),
            Arguments.of(
                "d", GuessResult.SuccessfulGuess.class
            )
        );
    }

}
