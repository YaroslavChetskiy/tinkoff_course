package edu.project1.console;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.HangmanDictionary;
import edu.project1.guessResult.GuessResult;
import edu.project1.session.GameStatus;
import edu.project1.session.Session;
import static edu.project1.utils.ConsoleWriterUtil.printMessage;
import static edu.project1.utils.ConsoleWriterUtil.printThemes;
import static edu.project1.utils.ThemesWordListUtil.getWordList;


public class ConsoleHangman {

    private static final String EXIT_CODE = "/quit";

    private static final int MAX_ATTEMPTS = 6;

    private final UserInputReader userInputReader;

    public ConsoleHangman(UserInputReader userInputReader) {
        this.userInputReader = userInputReader;
    }

    public void run() {
        printMessage("HANGMAN GAME STARTED!");
        while (true) {
            printThemes();
            Dictionary dictionary = new HangmanDictionary(getWordList(userInputReader.chooseTheme()));
            String answer = dictionary.getRandomWord();
            Session session = new Session(answer, MAX_ATTEMPTS);
            printMessage("Let's start! The word has length: " + answer.length());
            while (true) {
                printMessage("Guess the letter or give up with '/quit' command: ");
                String guess = userInputReader.getInput();
                GuessResult guessResult = tryGuess(session, guess.toLowerCase());
                printState(guessResult);
                if (guessResult.getStatus() == GameStatus.FINISHED) {
                    break;
                }
            }
            String startNewGameStatement = userInputReader.getInput();
            if (!startNewGameStatement.matches("(y|yes)")) {
                break;
            }
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
