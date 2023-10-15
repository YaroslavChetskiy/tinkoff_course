package edu.hw2.task3.executor;

import edu.hw2.task3.connectionManager.ConnectionManager;
import edu.hw2.task3.exception.ConnectionException;

public final class PopularCommandExecutor {
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
            } catch (Exception exception) {
                if (attempt == maxAttempts) {
                    throw new ConnectionException(exception.getMessage(), exception);
                }

            }
            attempt++;
        }
    }
}
