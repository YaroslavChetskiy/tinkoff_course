package edu.hw10.task2.proxy;

import edu.hw10.task2.calculator.FibonacciCalculator;
import edu.hw10.task2.calculator.RecursiveFibonacciCalculator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CacheProxyTest {

    private static final Path TEST_DIR = Path.of("src", "test", "resources", "hw10");

    @Test
    @DisplayName("Использование кэширующего прокси на числах Фибоначчи")
    void useCacheProxyInFibonacciCalculator() throws IOException {
        List<Long> fibonacciNumbers = List.of(1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L);
        FibonacciCalculator fibonacciCalculator = Mockito.mock(RecursiveFibonacciCalculator.class);
        FibonacciCalculator proxy = CacheProxy.create(fibonacciCalculator, fibonacciCalculator.getClass(), TEST_DIR);
        for (int i = 0; i < fibonacciNumbers.size(); i++) {
            doReturn(fibonacciNumbers.get(i)).when(fibonacciCalculator).fib(i + 1);
            var actualResult = proxy.fib(i + 1);
            assertThat(actualResult).isEqualTo(fibonacciNumbers.get(i));
            Mockito.verify(fibonacciCalculator, Mockito.times(1)).fib(i + 1);
        }
        var actualFile = TEST_DIR.resolve(fibonacciCalculator.getClass() + ".map");
        assertThat(Files.exists(actualFile)).isTrue();

        // check cache loading + using
        FibonacciCalculator proxy2 = CacheProxy.create(fibonacciCalculator, fibonacciCalculator.getClass(), TEST_DIR);
        for (int i = 0; i < fibonacciNumbers.size(); i++) {
            var actualResult = proxy2.fib(i + 1);
            assertThat(actualResult).isEqualTo(fibonacciNumbers.get(i));
            // с прошлого цикла количество вызовов метода осталось равно единице, поэтому оно не должно увеличиться
            Mockito.verify(fibonacciCalculator, Mockito.times(1)).fib(i + 1);
        }

        Files.delete(actualFile);
    }

    @Test
    @DisplayName("Выброс исключения при неправильной директории")
    void throwIllegalArgumentExceptionIfDirectoryIsInvalid() {
        FibonacciCalculator calculator = new RecursiveFibonacciCalculator();
        var exception = assertThrows(IllegalArgumentException.class, () -> CacheProxy.create(
                calculator,
                calculator.getClass(),
                TEST_DIR.resolve("empty.txt")
            )
        );
        assertThat(exception.getMessage()).isEqualTo("Path should be directory");
    }

}
