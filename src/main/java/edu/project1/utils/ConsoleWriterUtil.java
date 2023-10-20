package edu.project1.utils;

import edu.project1.dictionary.Theme;

@SuppressWarnings("RegexpSinglelineJava")
public final class ConsoleWriterUtil {

    private ConsoleWriterUtil() {
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printThemes() {
        System.out.println("Please, choose theme:");
        for (int i = 0; i < Theme.values().length; i++) {
            System.out.printf("%d) %s\n", i, Theme.values()[i]);
        }
        System.out.println();
    }
}
