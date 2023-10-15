package edu.hw2.task3.connectionManager;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;

public final class FaultyConnectionManager implements ConnectionManager {

    private final int errorFrequency;

    public FaultyConnectionManager(int errorFrequency) {
        this.errorFrequency = errorFrequency;
    }

    @Override
    public Connection getConnection() {
        return new FaultyConnection(errorFrequency);
    }
}
