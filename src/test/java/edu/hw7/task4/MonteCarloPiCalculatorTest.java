package edu.hw7.task4;

import java.util.List;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw7.task4.MonteCarloPiCalculator.estimateWithMultiThread;
import static edu.hw7.task4.MonteCarloPiCalculator.estimateWithSingleThread;
import static org.assertj.core.api.Assertions.assertThat;

class MonteCarloPiCalculatorTest {

    private static final List<Integer> TOTAL_COUNTS = List.of(1_000_000, 10_000_000, 100_000_000);
    private static final double OFFSET = 0.01;

    @Test
    @DisplayName("Получение числа Пи с погрешностью методом Монте-Карло, используя один поток")
    void estimatePiWithMonteCarloAlgorithmAndSingleThread() {
        for (Integer totalCount : TOTAL_COUNTS) {
            var actualResult = estimateWithSingleThread(totalCount);
            assertThat(actualResult).isEqualTo(Math.PI, Offset.offset(OFFSET));
        }
    }

    @Test
    @DisplayName("Получение числа Пи с погрешностью методом Монте-Карло, используя несколько потоков")
    void estimatePiWithMonteCarloAlgorithmAndMultiThread() {
        for (Integer totalCount : TOTAL_COUNTS) {
            var actualResult = estimateWithMultiThread(totalCount);
            assertThat(actualResult).isEqualTo(Math.PI, Offset.offset(OFFSET));
        }
    }

}
