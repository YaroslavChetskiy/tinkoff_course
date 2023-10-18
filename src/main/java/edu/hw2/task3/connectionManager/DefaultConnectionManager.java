package edu.hw2.task3.connectionManager;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import edu.hw2.task3.generator.IntegerGenerator;

public final class DefaultConnectionManager implements ConnectionManager {

    private static final int RIGHT_BOUND = 100;
    private static final int LEFT_BOUND = 0;

    private final IntegerGenerator integerGenerator;

    private final int errorPercent;
    private final int errorFrequency;

    public DefaultConnectionManager(int errorFrequency, int errorPercent, IntegerGenerator generator) {
        if (errorPercent < LEFT_BOUND || errorPercent > RIGHT_BOUND) {
            throw new IllegalArgumentException("Invalid error percent");
        }
        this.integerGenerator = generator;
        this.errorPercent = errorPercent;
        this.errorFrequency = errorFrequency;
    }

    @Override
    public Connection getConnection() {
        return integerGenerator.getRandomValue(LEFT_BOUND, RIGHT_BOUND) < errorPercent
            ? new FaultyConnection(errorFrequency)
            : new StableConnection();
    }
}
