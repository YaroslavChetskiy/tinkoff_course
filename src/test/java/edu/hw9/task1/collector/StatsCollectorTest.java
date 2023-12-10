package edu.hw9.task1.collector;

import edu.hw9.task1.statistics.StatisticsRecord;
import edu.hw9.task1.statistics.SummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StatsCollectorTest {

    private static final int NUMBER_OF_THREADS = 10;

    private static final Map<String, double[]> TEST_METRICS = Map.of(
        "first", new double[] {0.1, 0.05, 1.4, 5.1, 0.3},
        "second", new double[] {1, 2, 3, 4},
        "third", new double[] {0, 0, 0, 0, 0, 0},
        "fourth", new double[] {1, 1, 1},
        "fifth", new double[] {-1, -2, -3, -4, -5, -6, -7}
    );

    private static final List<StatisticsRecord> EXPECTED_STATISTICS = List.of(
        new StatisticsRecord(
            "first",
            List.of(0.1, 0.05, 1.4, 5.1, 0.3),
            new SummaryStatistics<>(5L, 0.05, 5.1, 6.95, 6.95 / 5L)
        ),
        new StatisticsRecord(
            "second",
            List.of(1.0, 2.0, 3.0, 4.0),
            new SummaryStatistics<>(4L, 1.0, 4.0, 10.0, 2.5)
        ),
        new StatisticsRecord(
            "third",
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            new SummaryStatistics<>(6L, 0.0, 0.0, 0.0, 0.0)
        ),
        new StatisticsRecord(
            "fourth",
            List.of(1.0, 1.0, 1.0),
            new SummaryStatistics<>(3L, 1.0, 1.0, 3.0, 1.0)
        ),
        new StatisticsRecord(
            "fifth",
            List.of(-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0),
            new SummaryStatistics<>(7L, -7.0, -1.0, -28.0, -4.0)
        )
    );

    @Test
    @DisplayName("Сбор метрик и получение статистики")
    void getMetricsStatistics() throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        var statsCollector = new StatsCollector(NUMBER_OF_THREADS);
        var latch = new CountDownLatch(TEST_METRICS.size());
        for (var entry : TEST_METRICS.entrySet()) {
            executorService.execute(() -> {
                statsCollector.push(entry.getKey(), entry.getValue());
                latch.countDown();
            });
        }
        latch.await();

        var actualResult = statsCollector.stats();
        assertThat(actualResult).hasSize(EXPECTED_STATISTICS.size()).containsAll(EXPECTED_STATISTICS);

        executorService.shutdown();
    }

}
