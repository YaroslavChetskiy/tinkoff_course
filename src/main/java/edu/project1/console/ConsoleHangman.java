package edu.project1.console;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.HangmanDictionary;
import edu.project1.dictionary.Theme;
import edu.project1.guessResult.GuessResult;
import edu.project1.session.Session;
import edu.project1.utils.ThemesWordListUtil;
import java.util.Scanner;

@SuppressWarnings("RegexpSinglelineJava")
public class ConsoleHangman {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final int MAX_ATTEMPTS = 6;

    public void run() {
        System.out.println("""
            HANGMAN GAME STARTED!
            """);
        while (true) {
            System.out.println("""
                Please, choose theme:
                """);
            for (int i = 0; i < Theme.values().length; i++) {
                System.out.printf("%d) %s\n", i, Theme.values()[i]);
            }
            System.out.println();
            String input = SCANNER.next();
            while (!input.matches("[0-%d]".formatted(Theme.values().length))) {
                System.out.println("""
                    Incorrect value!
                    Choose theme:
                    """);
                input = SCANNER.next();
            }
            Theme selectedTheme = Theme.values()[Integer.parseInt(input)];
            Dictionary dictionary = new HangmanDictionary(ThemesWordListUtil.getWordList(selectedTheme));
            String answer = dictionary.getRandomWord();
            Session session = new Session(answer, MAX_ATTEMPTS);
            System.out.println("Let's start! The word has length: " + answer.length());
            while (true) {
                System.out.println("Guess the letter or give up with '/quit' command: ");
                String guess = SCANNER.next();
                GuessResult guessResult = tryGuess(session, guess.toLowerCase());
                printState(guessResult);
                if (guessResult instanceof GuessResult.Win
                    || guessResult instanceof GuessResult.Defeat) {
                    break;
                }
            }
            String startNewGameStatement = SCANNER.next();
            if (!startNewGameStatement.matches("([yY]|Yes|YES)")) {
                break;
            }
        }
        System.out.println("Goodbye!");
    }

    protected GuessResult tryGuess(Session session, String input) {
        if (!input.matches("(/quit|[a-zA-Z])")) {
            return session.retry();
        }

        if ("/quit".equals(input)) {
            return session.giveUp();
        }
        return session.guess(input.charAt(0));
    }

    private void printState(GuessResult guessResult) {
        System.out.println(guessResult.getMessage());
    }
}
