package edu.project1.session;

import edu.project1.guessResult.GuessResult;
import edu.project1.guessResult.GuessResult.Defeat;
import edu.project1.guessResult.GuessResult.FailedGuess;
import edu.project1.guessResult.GuessResult.Retry;
import edu.project1.guessResult.GuessResult.SuccessfulGuess;
import edu.project1.guessResult.GuessResult.Win;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public class Session {

    private static final char HIDDEN = '_';

    private final String answer;

    private final char[] userAnswer;

    private final int maxAttempts;

    private int attempts;

    public Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        userAnswer = new char[answer.length()];
        Arrays.fill(userAnswer, HIDDEN);
    }

    @NotNull
    public GuessResult guess(char letter) {
        int firstIndex = answer.indexOf(letter);
        if (firstIndex != -1) {
            for (int i = firstIndex; i < userAnswer.length; i++) {
                if (answer.charAt(i) == letter) {
                    userAnswer[i] = letter;
                }
            }
            return new String(userAnswer).indexOf(HIDDEN) == -1
                ? new Win(userAnswer, attempts, maxAttempts, answer)
                : new SuccessfulGuess(userAnswer, attempts, maxAttempts, answer);
        } else {
            attempts++;
            return attempts == maxAttempts
                ? new Defeat(userAnswer, attempts, maxAttempts, answer)
                : new FailedGuess(userAnswer, attempts, maxAttempts, answer);
        }
    }

    @NotNull
    public GuessResult giveUp() {
        return new Defeat(userAnswer, attempts, maxAttempts, answer);
    }

    public GuessResult retry() {
        return new Retry(userAnswer, attempts, maxAttempts, answer);
    }
}
