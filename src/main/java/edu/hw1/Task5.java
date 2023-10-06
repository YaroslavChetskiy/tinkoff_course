package edu.hw1;

public final class Task5 {

    private Task5() {
    }

    private static boolean isPalindrome(String stringValue) {
        boolean result = true;
        int startIndex = 0;
        int endIndex = stringValue.length() - 1;
        while (startIndex < endIndex) {
            if (stringValue.charAt(startIndex) != stringValue.charAt(endIndex)) {
                result = false;
                break;
            }
            startIndex++;
            endIndex--;
        }
        return result;
    }

    public static boolean isPalindromeDescendant(int number) {
        String stringValue = Integer.toString(number);
        if (stringValue.length() < 2) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < stringValue.length(); i += 2) {
            int firstDigit = Character.getNumericValue(stringValue.charAt(i));
            int secondDigit = Character.getNumericValue(stringValue.charAt(i - 1));
            stringBuilder.append(firstDigit + secondDigit);
        }
        int descendant = Integer.parseInt(stringBuilder.toString());
        return isPalindrome(stringValue) || (stringValue.length() % 2 == 0 && isPalindromeDescendant(descendant));
    }
}
