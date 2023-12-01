package edu.hw8.task2.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FixedThreadPoolTest {

    private static final int NUMBER_OF_THREADS = 10;

    @Test
    @DisplayName("Параллельное вычисление чисел Фибоначчи")
    void parallelFibonacciCalculating() {
        var fibonacciNumbers = List.of(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        List<Integer> list = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(fibonacciNumbers.size());
        try (ThreadPool pool = FixedThreadPool.create(NUMBER_OF_THREADS)) {
            pool.start();
            for (int i = 0; i < fibonacciNumbers.size(); i++) {
                final int temp = i;
                pool.execute(() -> {
                    var kthFibonacciNumber = getKthFibonacciNumber(temp);
                    list.add(kthFibonacciNumber);
                    assertThat(kthFibonacciNumber).isEqualTo(fibonacciNumbers.get(temp));
                    latch.countDown();
                });
            }
            latch.await();
            assertThat(list).hasSize(fibonacciNumbers.size()).containsAll(fibonacciNumbers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getKthFibonacciNumber(int n) {
        if (n <= 1) {
            return 1;
        }
        return getKthFibonacciNumber(n - 1) + getKthFibonacciNumber(n - 2);
    }

}
