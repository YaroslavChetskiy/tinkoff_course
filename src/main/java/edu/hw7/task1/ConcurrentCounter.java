package edu.hw7.task1;

import edu.hw7.exception.MultiThreadException;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increaseCounter(int times) {
        Thread thread1 = new Thread(() -> increase(times / 2 + times % 2));
        Thread thread2 = new Thread(() -> increase(times / 2));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new MultiThreadException("Could not complete increasing", e);
        }
    }

    public int getCounterValue() {
        return counter.get();
    }

    private void increase(int times) {
        for (int i = 0; i < times; i++) {
            counter.incrementAndGet();
        }
    }
}
