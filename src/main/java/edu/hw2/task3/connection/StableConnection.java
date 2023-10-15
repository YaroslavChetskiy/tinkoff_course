package edu.hw2.task3.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {

    private static final Logger LOGGER = LogManager.getLogger();

    public StableConnection() {
    }

    @Override
    public void execute(String command) {
        LOGGER.info("Success execution: " + command);
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Connection closed");
    }
}
