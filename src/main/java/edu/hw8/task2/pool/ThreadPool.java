package edu.hw8.task2.pool;

public interface ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);
}
