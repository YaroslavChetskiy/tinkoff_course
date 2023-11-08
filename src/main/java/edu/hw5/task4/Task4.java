package edu.hw5.task4;

public final class Task4 {

    private static final String PASSWORD_REGEX = ".*[~!@#$%^&*|].*";

    private Task4() {
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}
