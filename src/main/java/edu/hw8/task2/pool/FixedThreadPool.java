package edu.hw8.task2.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {

    private final int numberOfThreads;

    private final Thread[] threads;

    private final BlockingQueue<Runnable> workQueue;

    public FixedThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        threads = new Thread[numberOfThreads];
        workQueue = new LinkedBlockingQueue<>();
    }

    public static FixedThreadPool create(int numberOfThreads) {
        return new FixedThreadPool(numberOfThreads);
    }

    @Override
    public void start() {
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                boolean isRunning = true;
                while (isRunning) {
                    try {
                        var task = workQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        isRunning = false;
                    }
                }
            });
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            workQueue.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException("Could not process task", e);
        }
    }

    @Override
    public void close() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
