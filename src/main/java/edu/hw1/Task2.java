package edu.hw1;

public final class Task2 {

    private Task2() {
    }

    // Деление на 10 навряд ли считается магическим числом,
    // хотя, может быть, я не прав
    @SuppressWarnings("MagicNumber")
    public static int countDigits(int number) {
        int temp = number > 0 ? number : -number;
        int count = 0;
        while (temp > 0) {
            count++;
            temp /= 10;
        }
        return number == 0 ? 1 : count;
    }
}
