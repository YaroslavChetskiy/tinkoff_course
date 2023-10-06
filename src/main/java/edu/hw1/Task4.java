package edu.hw1;

public final class Task4 {

    private Task4() {
    }

    public static String fixString(String incorrectString) {
        if (incorrectString == null) {
            throw new NullPointerException();
        }
        var stringBuilder = new StringBuilder();
        for (int i = 1; i < incorrectString.length(); i += 2) {
            stringBuilder.append(incorrectString.charAt(i));
            stringBuilder.append(incorrectString.charAt(i - 1));
        }
        if (incorrectString.length() % 2 != 0) {
            stringBuilder.append(incorrectString.charAt(incorrectString.length() - 1));
        }
        return stringBuilder.toString();
    }
}
