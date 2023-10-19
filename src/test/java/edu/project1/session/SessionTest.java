package edu.project1.session;

import edu.project1.guessResult.GuessResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {

    private static final int DEFAULT_MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Изменение количества попыток при поражении")
    void shouldChangeStateAndAttemptsInReachingMaxAttempts() {
        Session session = new Session("dummy", 1);
        var actualResult = session.guess('a');
        assertThat(actualResult).isInstanceOf(GuessResult.Defeat.class);
        assertThat(actualResult.getAttempt()).isOne();
    }

    @ParameterizedTest
    @DisplayName("Возвращение корректного GuessResult")
    @MethodSource("getArgumentsForGuessResultTest")
    void getGuessResult(String answer, char guess, int maxAttempts, Class<? extends GuessResult> expectedResult) {
        Session session = new Session(answer, maxAttempts);
        var actualResult = session.guess(guess);
        assertThat(actualResult).isInstanceOf(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGuessResultTest() {
        return Stream.of(
            Arguments.of("car", 'a', DEFAULT_MAX_ATTEMPTS, GuessResult.SuccessfulGuess.class),
            Arguments.of("dummy", 'b', DEFAULT_MAX_ATTEMPTS, GuessResult.FailedGuess.class),
            Arguments.of("a", 'b', 1, GuessResult.Defeat.class),
            Arguments.of("a", 'a', DEFAULT_MAX_ATTEMPTS, GuessResult.Win.class)
        );
    }

    @ParameterizedTest
    @DisplayName("Увеличение количества использованных попыток и сохранение состояния при неугадывании")
    @MethodSource("getArgumentsForFailedGuessTest")
    void shouldIncreaseAttemptsAndNoChangeStateInFailedGuess(
        String answer,
        List<Character> guesses,
        char[] expectedState
    ) {
        Session session = new Session(answer, DEFAULT_MAX_ATTEMPTS);
        GuessResult actualResult = null;
        for (Character guess : guesses) {
            actualResult = session.guess(guess);
        }
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getState()).isEqualTo(expectedState);
        assertThat(actualResult.getAttempt()).isEqualTo(guesses.size());
    }

    static Stream<Arguments> getArgumentsForFailedGuessTest() {
        return Stream.of(
            Arguments.of("car", List.of('d', 'b', 'g'), new char[] {'_', '_', '_'}),
            Arguments.of("bird", List.of('a', 'c', 'e', 'k'), new char[] {'_', '_', '_', '_'}),
            Arguments.of("shark", List.of('d', 'g'), new char[] {'_', '_', '_', '_', '_'})
        );
    }

    @ParameterizedTest
    @DisplayName("Увеличение количества использованных попыток и сохранение состояния при неугадывании")
    @MethodSource("getArgumentsForSuccessGuessTest")
    void shouldChangeStateAndNoChangeAttemptsInSuccessGuess(
        String answer,
        List<Character> guesses,
        char[] expectedState
    ) {
        Session session = new Session(answer, DEFAULT_MAX_ATTEMPTS);
        GuessResult actualResult = null;
        for (Character guess : guesses) {
            actualResult = session.guess(guess);
        }
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getState()).isEqualTo(expectedState);
        assertThat(actualResult.getAttempt()).isZero();
    }

    static Stream<Arguments> getArgumentsForSuccessGuessTest() {
        return Stream.of(
            Arguments.of("car", List.of('c', 'a'), new char[] {'c', 'a', '_'}),
            Arguments.of("bird", List.of('i', 'r'), new char[] {'_', 'i', 'r', '_'}),
            Arguments.of("shark", List.of('r', 'k'), new char[] {'_', '_', '_', 'r', 'k'}),
            Arguments.of("ababa", List.of('a'), new char[] {'a', '_', 'a', '_', 'a'})
        );
    }

    @ParameterizedTest
    @DisplayName("Достижение победы при угадывании всех букв")
    @MethodSource("getArgumentsForWinGuessTest")
    void getWinGuessResultIfAllLettersGuessed(String answer, List<Character> guesses) {
        Session session = new Session(answer, DEFAULT_MAX_ATTEMPTS);
        for (int i = 0; i < guesses.size() - 1; i++) {
            session.guess(guesses.get(i));
        }
        var actualResult = session.guess(guesses.get(guesses.size() - 1));
        assertThat(actualResult).isInstanceOf(GuessResult.Win.class);
    }

    static Stream<Arguments> getArgumentsForWinGuessTest() {
        return Stream.of(
            Arguments.of("car", List.of('c', 'a', 'r')),
            Arguments.of("bird", List.of('b', 'i', 'r', 'd')),
            Arguments.of("ddddd", List.of('d')),
            Arguments.of("ababa", List.of('a', 'b'))
        );
    }

    @ParameterizedTest
    @DisplayName("Поражение при достижении maxAttempts")
    @ValueSource(ints = {1, 3, 5, 7, 10, 15})
    void getWinGuessResultIfAllLettersGuessed(int maxAttempts) {
        Session session = new Session("dummy", maxAttempts);
        for (int i = 0; i < maxAttempts - 1; i++) {
            session.guess('k');
        }
        var actualResult = session.guess('k');
        assertThat(actualResult).isInstanceOf(GuessResult.Defeat.class);
    }

    @Test
    @DisplayName("Проверка сдачи")
    void checkGiveUp() {
        Session session = new Session("dummy", DEFAULT_MAX_ATTEMPTS);
        var actualResult = session.giveUp();
        assertThat(actualResult).isInstanceOf(GuessResult.Defeat.class);
    }

    @Test
    @DisplayName("Проверка повтора")
    void checkRetry() {
        Session session = new Session("dummy", DEFAULT_MAX_ATTEMPTS);
        var actualResult = session.retry();
        assertThat(actualResult).isInstanceOf(GuessResult.Retry.class);
    }
}
