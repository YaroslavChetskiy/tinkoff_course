package edu.project1.guessResult;

import edu.project1.session.GameStatus;
import org.jetbrains.annotations.NotNull;

public sealed interface GuessResult {

    // Полезны для тестирования,
    // а также для масштабируемости кода
    char[] getState();

    int getAttempt();

    int getMaxAttempts();

    GameStatus getStatus();

    @NotNull
    String getMessage();

    record Defeat(char[] state, int attempt, int maxAttempts, String answer) implements GuessResult {

        @Override
        public char[] getState() {
            return state;
        }

        @Override
        public int getAttempt() {
            return attempt;
        }

        @Override
        public int getMaxAttempts() {
            return maxAttempts;
        }

        @Override
        public GameStatus getStatus() {
            return GameStatus.FINISHED;
        }

        @Override
        public @NotNull String getMessage() {
            return """
                DEFEAT:
                Oh, you lose! Your used attempts: %d of %d.
                Your result: %s.
                The correct answer is: %s.
                Do you wanna start new game? (y/n)
                """.formatted(attempt, maxAttempts, new String(state), answer);
        }
    }

    record Win(char[] state, int attempt, int maxAttempts, String answer) implements GuessResult {

        @Override
        public char[] getState() {
            return state;
        }

        @Override
        public int getAttempt() {
            return attempt;
        }

        @Override
        public int getMaxAttempts() {
            return maxAttempts;
        }

        @Override
        public GameStatus getStatus() {
            return GameStatus.FINISHED;
        }

        @Override
        public @NotNull String getMessage() {
            return """
                WIN:
                It's a right guess! You win in your %d of %d attempt!
                The correct answer is: %s.
                Do you wanna start new game? (y/n)
                """.formatted(attempt, maxAttempts, answer);
        }
    }

    record SuccessfulGuess(char[] state, int attempt, int maxAttempts, String answer) implements GuessResult {

        @Override
        public char[] getState() {
            return state;
        }

        @Override
        public int getAttempt() {
            return attempt;
        }

        @Override
        public int getMaxAttempts() {
            return maxAttempts;
        }

        @Override
        public GameStatus getStatus() {
            return GameStatus.IN_PROGRESS;
        }

        @Override
        public @NotNull String getMessage() {
            return """
                You guessed!
                Current state: %s
                """.formatted(new String(state));
        }
    }

    record FailedGuess(char[] state, int attempt, int maxAttempts, String answer) implements GuessResult {

        @Override
        public char[] getState() {
            return state;
        }

        @Override
        public int getAttempt() {
            return attempt;
        }

        @Override
        public int getMaxAttempts() {
            return maxAttempts;
        }

        @Override
        public GameStatus getStatus() {
            return GameStatus.IN_PROGRESS;
        }

        @Override
        public @NotNull String getMessage() {
            return """
                Oh, you missed! Try again!
                Mistake: %d of %d
                Current state: %s
                """.formatted(attempt, maxAttempts, new String(state));
        }
    }

    record Retry(char[] state, int attempt, int maxAttempts, String answer) implements GuessResult {

        @Override
        public char[] getState() {
            return state;
        }

        @Override
        public int getAttempt() {
            return attempt;
        }

        @Override
        public int getMaxAttempts() {
            return maxAttempts;
        }

        @Override
        public GameStatus getStatus() {
            return GameStatus.IN_PROGRESS;
        }

        @Override
        public @NotNull String getMessage() {
            return "Invalid input, please try again";
        }
    }
}
