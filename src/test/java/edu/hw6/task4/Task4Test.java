package edu.hw6.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {

    private static final String TEST_STRING = "Programming is learned by writing programs. ― Brian Kernighan";

    private static final String TEST_PATH = "src/test/resources/hw6/task4.txt";

    @Test
    @DisplayName("Запись тестовой строки в файл, используя цепочку Output Stream'ов")
    void writeTestStringToFileUsingOutputChain() throws IOException {
        var expectedResult = List.of(TEST_STRING);
        Task4.writeToFileUsingOutputChain(TEST_PATH, TEST_STRING);

        var lines = Files.readAllLines(Path.of(TEST_PATH));
        assertThat(lines).isEqualTo(expectedResult);
    }

}
