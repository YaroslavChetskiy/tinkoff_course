package edu.hw7.task4;

import edu.hw7.exception.MultiThreadException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class MonteCarloPiCalculator {

    private static final int RADIUS = 1;
    private static final int SQUARE_SIDE_SIZE = 2;
    public static final double MONTE_CARLO_CONST = 4.0;

    public static final int DEFAULT_NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    private MonteCarloPiCalculator() {
    }

    public static double estimateWithSingleThread(int totalCount) {
        int circleCount = calculateCirclePointsCount(totalCount);
        return MONTE_CARLO_CONST * circleCount / totalCount;
    }

    public static double estimateWithMultiThread(int totalCount) {
        return estimateWithMultiThread(totalCount, DEFAULT_NUMBER_OF_THREADS);
    }

    public static double estimateWithMultiThread(int totalCount, int numberOfThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        AtomicInteger circleCount = new AtomicInteger(0);

        Callable<Void> callable = () -> {
            int insideCircle = calculateCirclePointsCount(totalCount / numberOfThreads);
            circleCount.addAndGet(insideCircle);
            return null;
        };

        var tasks = Stream.generate(() -> callable).limit(numberOfThreads).toList();

        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new MultiThreadException("Error in multi thread calculations", e);
        } finally {
            executorService.shutdown();
        }

        return MONTE_CARLO_CONST * circleCount.get() / totalCount;
    }

    private static int calculateCirclePointsCount(int totalCount) {
        int circleCount = 0;
        var random = ThreadLocalRandom.current();
        for (int i = 0; i < totalCount; i++) {
            // пусть у нас квадрат и окружность с центром в (0,0)
            // получаем точки в промежутке (-1, 1)
            double x = random.nextDouble() * SQUARE_SIDE_SIZE - RADIUS;
            double y = random.nextDouble() * SQUARE_SIDE_SIZE - RADIUS;
            if (x * x + y * y <= RADIUS) {
                circleCount++;
            }
        }
        return circleCount;
    }

}
