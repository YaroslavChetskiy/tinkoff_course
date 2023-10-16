package edu.hw2.task3.connectionManager;

import edu.hw2.task3.connection.Connection;
import edu.hw2.task3.connection.FaultyConnection;
import edu.hw2.task3.connection.StableConnection;
import edu.hw2.task3.generator.IntegerGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class DefaultConnectionManagerTest {

    @Mock
    private IntegerGenerator integerGenerator;

    private static final int ERROR_FREQUENCY = 1;

    @ParameterizedTest
    @DisplayName("Получение соединения")
    @MethodSource("getArgumentsForGetConnectionTest")
    void getConnection(int errorPercent, Class<? extends Connection> connectionClass, int needPercent) {
        doReturn(needPercent).when(integerGenerator).getRandomValue(anyInt(), anyInt());
        var defaultConnectionManager = new DefaultConnectionManager(ERROR_FREQUENCY, errorPercent, integerGenerator);
        var actualResult = defaultConnectionManager.getConnection();
        assertThat(actualResult).isInstanceOf(connectionClass);
    }

    static Stream<Arguments> getArgumentsForGetConnectionTest() {
        return Stream.of(
            Arguments.of(0, StableConnection.class, 70),
            Arguments.of(100, FaultyConnection.class, 99),
            Arguments.of(30, FaultyConnection.class, 20),
            Arguments.of(30, StableConnection.class, 50)
        );
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неправильном проценте ошибки")
    @ValueSource(ints = {-100, 999})
    void throwIllegalArgumentExceptionIfErrorPercentIsInvalid(int errorPercent) {
        var exception = assertThrows(
            IllegalArgumentException.class,
            () -> new DefaultConnectionManager(ERROR_FREQUENCY, errorPercent, integerGenerator)
        );
        assertThat(exception.getMessage()).isEqualTo("Invalid error percent");
    }
}
