package edu.project1.console;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.HangmanDictionary;
import edu.project1.guessResult.GuessResult;
import edu.project1.session.GameStatus;
import edu.project1.session.Session;
import static edu.project1.utils.ConsoleWriterUtil.printMessage;
import static edu.project1.utils.ConsoleWriterUtil.printThemes;


public class ConsoleHangman {

    private static final String EXIT_CODE = "/quit";

    private static final int MAX_ATTEMPTS = 6;

    private final UserInputReader userInputReader;

    public ConsoleHangman(UserInputReader userInputReader) {
        this.userInputReader = userInputReader;
    }

    public void run() {
        printMessage("HANGMAN GAME STARTED!");
        boolean startNewGame = true;
        while (startNewGame) {
            printThemes();
            Dictionary dictionary = new HangmanDictionary(userInputReader.chooseTheme());
            String answer = dictionary.getRandomWord();
            Session session = new Session(answer, MAX_ATTEMPTS);
            printMessage("Let's start! The word has length: " + answer.length());
            boolean gameInProgress = true;
            while (gameInProgress) {
                printMessage("Guess the letter or give up with '/quit' command: ");
                String guess = userInputReader.getInput();
                GuessResult guessResult = tryGuess(session, guess.toLowerCase());
                printState(guessResult);
                gameInProgress = guessResult.getStatus() == GameStatus.IN_PROGRESS;
            }
            String startNewGameStatement = userInputReader.getInput();
            startNewGame = startNewGameStatement.matches("(y|yes)");
        }
        printMessage("Goodbye!");
    }

    protected GuessResult tryGuess(Session session, String input) {
        if (EXIT_CODE.equals(input)) {
            return session.giveUp();
        }

        if (!input.matches("[a-zA-Z]")) {
            return session.retry();
        }

        return session.guess(input.charAt(0));
    }

    private void printState(GuessResult guessResult) {
        printMessage(guessResult.getMessage());
    }
}
