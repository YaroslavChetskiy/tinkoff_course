package edu.project1.guessResult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class GuessResultTest {

    @ParameterizedTest
    @DisplayName("Проверка возвращения правильного сообщения")
    @MethodSource("getArgumentsForGetMessageChecking")
    void checkGetMessage(GuessResult guessResult, String expectedResult) {
        String actualResult = guessResult.getMessage();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetMessageChecking() {
        return Stream.of(
            Arguments.of(
                new GuessResult.Defeat(new char[] {'_', '_'}, 1, 5, "dummy"),
                """
                    DEFEAT:
                    Oh, you lose! Your used attempts: 1 of 5.
                    Your result: __.
                    The correct answer is: dummy.
                    Do you wanna start new game? (y/n)
                    """
            ),
            Arguments.of(
                new GuessResult.Win(new char[] {'a', 'b'}, 0, 5, "dummy"),
                """
                    WIN:
                    It's a right guess! You win in your 0 of 5 attempt!
                    The correct answer is: dummy.
                    Do you wanna start new game? (y/n)
                    """
            ),
            Arguments.of(
                new GuessResult.SuccessfulGuess(new char[] {'_', 'b'}, 0, 5, "dummy"),
                """
                    You guessed!
                    Current state: _b
                    """
            ),
            Arguments.of(
                new GuessResult.FailedGuess(new char[] {'_', '_'}, 1, 5, "dummy"),
                """
                    Oh, you missed! Try again!
                    Mistake: 1 of 5
                    Current state: __
                    """
            ),
            Arguments.of(
                new GuessResult.Retry(new char[] {'_', '_'}, 1, 5, "dummy"),
                "Invalid input, please try again"
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение попытки")
    @MethodSource("getArgumentsForGetAttemptTest")
    void checkGetAttempt(GuessResult guessResult, int expectedResult) {
        var actualResult = guessResult.getAttempt();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetAttemptTest() {
        return Stream.of(
            Arguments.of(
                new GuessResult.Defeat(new char[] {'_', '_'}, 1, 5, "dummy"),
                1
            ),
            Arguments.of(
                new GuessResult.Win(new char[] {'a', 'b'}, 0, 5, "dummy"),
                0
            ),
            Arguments.of(
                new GuessResult.SuccessfulGuess(new char[] {'_', 'b'}, 2, 5, "dummy"),
                2
            ),
            Arguments.of(
                new GuessResult.FailedGuess(new char[] {'_', '_'}, 3, 5, "dummy"),
                3
            ),
            Arguments.of(
                new GuessResult.Retry(new char[] {'_', '_'}, 2, 5, "dummy"),
                2
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение попытки")
    @MethodSource("getArgumentsForGetStateTest")
    void checkGetState(GuessResult guessResult, char [] expectedResult) {
        var actualResult = guessResult.getState();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetStateTest() {
        return Stream.of(
            Arguments.of(
                new GuessResult.Defeat(new char[] {'_', '_'}, 1, 5, "dummy"),
                new char[] {'_', '_'}
            ),
            Arguments.of(
                new GuessResult.Win(new char[] {'a', 'b'}, 0, 5, "dummy"),
                new char[] {'a', 'b'}
            ),
            Arguments.of(
                new GuessResult.SuccessfulGuess(new char[] {'_', 'b'}, 2, 5, "dummy"),
                new char[] {'_', 'b'}
            ),
            Arguments.of(
                new GuessResult.FailedGuess(new char[] {'_', '_'}, 3, 5, "dummy"),
                new char[] {'_', '_'}
            ),
            Arguments.of(
                new GuessResult.Retry(new char[] {'_', '_'}, 2, 5, "dummy"),
                new char[] {'_', '_'}
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение попытки")
    @MethodSource("getArgumentsForGetMaxAttemptsTest")
    void checkGetMaxAttempts(GuessResult guessResult, int expectedResult) {
        var actualResult = guessResult.getMaxAttempts();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetMaxAttemptsTest() {
        return Stream.of(
            Arguments.of(
                new GuessResult.Defeat(new char[] {'_', '_'}, 1, 6, "dummy"),
                6
            ),
            Arguments.of(
                new GuessResult.Win(new char[] {'a', 'b'}, 0, 5, "dummy"),
                5
            ),
            Arguments.of(
                new GuessResult.SuccessfulGuess(new char[] {'_', 'b'}, 2, 3, "dummy"),
                3
            ),
            Arguments.of(
                new GuessResult.FailedGuess(new char[] {'_', '_'}, 3, 99, "dummy"),
                99
            ),
            Arguments.of(
                new GuessResult.Retry(new char[] {'_', '_'}, 2, 8, "dummy"),
                8
            )
        );
    }
}
