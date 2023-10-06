package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw1.Task6.countK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task6Test {

    @ParameterizedTest
    @DisplayName("Получение количества итераций функции Капрекара")
    @CsvSource(value = {
        "3524, 3",
        "6621, 5",
        "6554, 4",
        "1234, 3"
    })
    void getCountIfNumberIsCorrect(int number, int expectedResult) {
        var actualResult = countK(number);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неверном числе")
    @CsvSource(value = {
        "23",
        "6666",
        "9999",
        "1111"
    })
    void throwIllegalArgumentExceptionIfNumberIsNotCorrect(int number) {
        var exception = assertThrows(IllegalArgumentException.class, () -> countK(number));
        assertThat(exception.getMessage()).isEqualTo("Invalid number");
    }
}
