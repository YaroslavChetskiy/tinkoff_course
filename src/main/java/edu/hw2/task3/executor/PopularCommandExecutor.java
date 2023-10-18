package edu.hw2.task3.executor;

import edu.hw2.task3.connectionManager.ConnectionManager;
import edu.hw2.task3.exception.ConnectionException;
import edu.hw2.task3.exception.ExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PopularCommandExecutor {

    private static final Logger LOGGER = LogManager.getLogger();
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        int attempt = 1;
        while (attempt <= maxAttempts) {
            try (var connection = manager.getConnection()) {
                connection.execute(command);
                break;
            } catch (ConnectionException exception) {
                if (attempt == maxAttempts) {
                    throw new ExecutionException("Execution failed: " + exception.getMessage(), exception);
                }
            } catch (Exception exception) {
                LOGGER.error("Error in closing connection");
            }
            attempt++;
        }
    }
}
