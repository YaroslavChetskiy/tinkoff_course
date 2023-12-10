package edu.hw9.task1.collector;

import edu.hw9.task1.statistics.StatisticsRecord;
import edu.hw9.task1.statistics.SummaryStatistics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatsCollector {

    private final Map<String, List<Double>> metricsMap = new ConcurrentHashMap<>();

    private final int numberOfThreads;

    public StatsCollector(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void push(String metricName, double[] values) {
        metricsMap.putIfAbsent(metricName, new CopyOnWriteArrayList<>());
        var metricValues = metricsMap.get(metricName);
        for (var value : values) {
            metricValues.add(value);
        }
    }

    public List<StatisticsRecord> stats() {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<CompletableFuture<StatisticsRecord>> futures = new ArrayList<>();
        try {
            for (var entry : metricsMap.entrySet()) {
                var future = CompletableFuture.supplyAsync(
                    () -> {
                        var statistics = entry.getValue()
                            .parallelStream()
                            .mapToDouble(Double::valueOf)
                            .summaryStatistics();
                        return new StatisticsRecord(
                            entry.getKey(),
                            entry.getValue(),
                            new SummaryStatistics<>(
                                statistics.getCount(),
                                statistics.getMin(),
                                statistics.getMax(),
                                statistics.getSum(),
                                statistics.getAverage()
                            )
                        );
                    },
                    executorService
                );

                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
            return futures.stream()
                .map(CompletableFuture::join)
                .toList();
        } finally {
            executorService.shutdown();
        }
    }
}
