package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw1.Task5.isPalindromeDescendant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task5Test {

    @ParameterizedTest
    @DisplayName("Проверка числа на особый палиндром")
    @CsvSource(value = {
        "123312, true",
        "11211230, true",
        "13001120, true",
        "23336014, true",
        "11, true",
        "5, false",
        "123, false",
        "54321, false",
    })
    void checkIsPalindromeDescendant(int number, boolean expectedResult) {
        boolean actualResult = isPalindromeDescendant(number);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
