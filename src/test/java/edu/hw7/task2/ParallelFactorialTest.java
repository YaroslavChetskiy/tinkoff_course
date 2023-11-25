package edu.hw7.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw7.task2.ParallelFactorial.getFactorial;
import static org.assertj.core.api.Assertions.assertThat;

class ParallelFactorialTest {

    @ParameterizedTest
    @DisplayName("Параллельное вычисление факториала")
    @CsvSource(value = {
        "1, 1",
        "2, 2",
        "3, 6",
        "10, 3628800",
        "14, 87178291200"
    })
    void parallelFactorialCalculation(long n, long expectedResult) {
        var actualResult = getFactorial(n);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}
