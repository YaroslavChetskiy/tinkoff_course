package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw1.Task2.countDigits;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    @ParameterizedTest
    @DisplayName("Подсчёт количества цифр в числе")
    @CsvSource(value = {
        "4666, 4",
        "544, 3",
        "22, 2",
        "0, 1",
        "-999, 3"
    })
    void getCorrectDigitsCount(int number, int expectedResult) {
        int actualResult = countDigits(number);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
