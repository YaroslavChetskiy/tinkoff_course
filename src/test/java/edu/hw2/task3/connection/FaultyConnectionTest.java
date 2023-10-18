package edu.hw2.task3.connection;

import edu.hw2.task3.exception.ConnectionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

// Я пытался делать тестирование logger'а, но даже используя Mock'и, у меня не получилось
class FaultyConnectionTest {

    @Test
    @DisplayName("Выброс исключения, если частота ошибки меньше 1")
    void throwIllegalArgumentExceptionIfErrorFrequencyLessThenOne() {
        // не уверен, нужно ли в таких случаях делать try-with-resources
        assertThrows(IllegalArgumentException.class, () -> new FaultyConnection(0));
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при достижении частоты ошибки")
    @ValueSource(ints = {1, 5, 10, 12})
    void throwConnectionExceptionIfErrorFrequencyReached(int errorFrequency) throws Exception {
        try (var faultyConnection = new FaultyConnection(errorFrequency)) {
            for (int i = 0; i < errorFrequency - 1; i++) {
                faultyConnection.execute("dummy");
            }
            assertThrows(ConnectionException.class, () -> faultyConnection.execute("dummy"));
        }
    }

}
