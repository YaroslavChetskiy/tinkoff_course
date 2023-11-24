package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentCounterTest {

    @ParameterizedTest
    @DisplayName("Увеличение счётчика times раз")
    @ValueSource(ints = {0, 1, 100, 1000, 100_000, 123, 321})
    void getIncreasedCounter(int times) {
        var counter = new ConcurrentCounter();
        counter.increaseCounter(times);
        var actualResult = counter.getCounterValue();
        assertThat(actualResult).isEqualTo(times);
    }

}
