package edu.hw2.task3.connectionManager;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultConnectionManagerTest {

    private static final int ERROR_FREQUENCY = 1;

    @ParameterizedTest
    @DisplayName("Получение соединения")
    @MethodSource("getArgumentsForGetConnectionTest")
    void getConnection(int errorPercent, Class<? extends Connection> connectionClass) {
        var defaultConnectionManager = new DefaultConnectionManager(ERROR_FREQUENCY, errorPercent);
        var actualResult = defaultConnectionManager.getConnection();
        assertThat(actualResult).isInstanceOf(connectionClass);
    }

    static Stream<Arguments> getArgumentsForGetConnectionTest() {
        return Stream.of(
            Arguments.of(0, StableConnection.class),
            Arguments.of(100, FaultyConnection.class)
        );
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неправильном проценте ошибки")
    @ValueSource(ints = {-100, 999})
    void throwIllegalArgumentExceptionIfErrorPercentIsInvalid(int errorPercent) {
        var exception = assertThrows(
            IllegalArgumentException.class,
            () -> new DefaultConnectionManager(ERROR_FREQUENCY, errorPercent)
        );
        assertThat(exception.getMessage()).isEqualTo("Invalid error percent");
    }
}
