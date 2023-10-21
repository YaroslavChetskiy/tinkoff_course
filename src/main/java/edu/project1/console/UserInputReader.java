package edu.project1.console;

import edu.project1.dictionary.Theme;
import java.util.Scanner;
import static edu.project1.utils.ConsoleWriterUtil.printMessage;

public class UserInputReader {
    private final Scanner scanner;

    public UserInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getInput() {
        return scanner.hasNext() ? scanner.next().toLowerCase() : null;
    }

    public Theme chooseTheme() {
        String input = getInput();
        while (input == null || !validateInputTheme(input)) {
            printMessage("""
                Incorrect value!
                Choose theme:""");
            input = getInput();
        }
        return Theme.values()[Integer.parseInt(input)];
    }

    // решил, что такая валидация будет более интуитивно понятна,
    // чем регулярные выражения
    private boolean validateInputTheme(String input) {
        try {
            int choice = Integer.parseInt(input);
            return choice >= 0 && choice < Theme.values().length;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
