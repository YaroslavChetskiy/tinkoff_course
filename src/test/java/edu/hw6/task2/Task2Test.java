package edu.hw6.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import static edu.hw6.task2.Task2.cloneFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    private static final Path COPY_TEST_PATH = Path.of("src", "test", "resources", "hw6/copies");

    @ParameterizedTest
    @DisplayName("Создание копии файла с правильным окончанием файла")
    @MethodSource("getArgumentsForCopyFileWithRightEndingTest")
    void copyFileWithRightNameEnding(String fileName, String expectedResult) throws IOException {
        Path filePath = COPY_TEST_PATH.resolve(fileName);
        Path expectedPath = COPY_TEST_PATH.resolve(expectedResult);
        cloneFile(filePath);
        assertThat(Files.exists(expectedPath)).isTrue();
        if (!Files.isDirectory(expectedPath)) {
            assertThat(Files.readAllLines(filePath)).isEqualTo(Files.readAllLines(expectedPath));
        }
        Files.delete(expectedPath);
    }

    static Stream<Arguments> getArgumentsForCopyFileWithRightEndingTest() {
        return Stream.of(
            Arguments.of(
                "dir",
                "dir — копия"
            ),
            Arguments.of(
                "hasCopies",
                "hasCopies — копия (4)"
            ),
            Arguments.of(
                "text.txt",
                "text — копия.txt"
            )
        );
    }

    @Test
    @DisplayName("Выброс исключения, если файла не существует")
    void throwRuntimeExceptionIfFileDoesNotExist() {
        var nonExistingFilePath = COPY_TEST_PATH.resolve("error.txt");
        assertThrows(RuntimeException.class, () -> cloneFile(nonExistingFilePath));
    }
}
