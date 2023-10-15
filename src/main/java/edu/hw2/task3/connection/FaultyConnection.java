package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {

    private static final Logger LOGGER = LogManager.getLogger();
    private final int errorFrequency;
    private int attempt = 0;

    public FaultyConnection(int errorFrequency) {
        if (errorFrequency < 1) {
            throw new IllegalArgumentException("Invalid error frequency");
        }
        this.errorFrequency = errorFrequency;
    }

    @Override
    public void execute(String command) {
        attempt++;
        if (attempt % errorFrequency == 0) {
            throw new ConnectionException("Could not execute: " + command);
        } else {
            LOGGER.info("Success execution: " + command);
        }
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Connection closed");
    }
}
