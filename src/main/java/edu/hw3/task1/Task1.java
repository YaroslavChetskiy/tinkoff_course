package edu.hw3.task1;

import static java.lang.Character.isUpperCase;

public final class Task1 {

    private Task1() {
    }

    public static String atbashCipher(String inputString) {
        if (inputString == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            char letter = inputString.charAt(i);
            if (!isLatinCharacter(letter)) {
                stringBuilder.append(letter);
                continue;
            }
            stringBuilder.append(encodeLetter(letter));
        }
        return stringBuilder.toString();
    }

    private static char encodeLetter(char letter) {
        if (isUpperCase(letter)) {
            return (char) ('Z' - letter + 'A');
        } else {
            return (char) ('z' - letter + 'a');
        }
    }

    private static boolean isLatinCharacter(char letter) {
        return (letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z');
    }

}
