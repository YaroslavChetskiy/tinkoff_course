package edu.hw2.task3.executor;

import edu.hw2.task3.connectionManager.FaultyConnectionManager;
import edu.hw2.task3.exception.ConnectionException;
import edu.hw2.task3.exception.ExecutionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Пока у меня плохо с использованием Mockito, одни ошибки при тестировании Random и Logger
// Поэтому пока без их тестирования
class PopularCommandExecutorTest {

    private static final int MAX_ATTEMPTS = 5;
    private static final int ERROR_FREQUENCY = 1;

    @Test
    @DisplayName("Выброс исключения при достижении maxAttempts")
    void throwExecutionExceptionIfMaxAttemptsReachedInUpdatingPackages() {
        var popularCommandExecutor =
            new PopularCommandExecutor(new FaultyConnectionManager(ERROR_FREQUENCY), MAX_ATTEMPTS);
        var exception = assertThrows(ExecutionException.class, popularCommandExecutor::updatePackages);
        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Execution failed: " + exception.getCause().getMessage());
    }
}
