package edu.hw9.task2.handler;

import edu.hw9.task2.exception.FileSystemHandlerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSystemHandlerTest {

    private static final int NUMBER_OF_THREADS = 5;

    private static final int FILES_COUNT = 4;


    private static final String FILE_EXTENSION = ".txt";

    private static final Path TEST_PATH = Path.of("src", "test", "resources", "hw9", "task3");


    @Test
    @DisplayName("Получение директорий с больше, чем N файлами ")
    void getDirectoriesWithMoreThanNFiles() {
        var expectedResult = List.of(
            TEST_PATH.resolve("dir1"),
            TEST_PATH.resolve("dir2/dir3")
        );

        var fileSystemHandler = new FileSystemHandler(NUMBER_OF_THREADS);
        var actualResult = fileSystemHandler.getDirectoriesWithMoreThanNthFiles(TEST_PATH, FILES_COUNT);
        assertThat(actualResult).hasSize(expectedResult.size()).containsAll(expectedResult);
    }

    @Test
    @DisplayName("Получение файлов с заданными размером и расширением")
    void getFilesWithSizeAndExtension() throws IOException {
        var expectedResult = List.of(
            TEST_PATH.resolve("dir2/2.txt"),
            TEST_PATH.resolve("dir2/3.txt"),
            TEST_PATH.resolve("dir2/dir3/4.txt"),
            TEST_PATH.resolve("dir2/dir3/5.txt"),
            TEST_PATH.resolve("dir1/6.txt")
        );
        long fileSize = Files.size(TEST_PATH.resolve("dir1/6.txt"));
        var fileSystemHandler = new FileSystemHandler(NUMBER_OF_THREADS);
        var actualResult = fileSystemHandler.getFilesWithSizeAndExtension(TEST_PATH, fileSize, FILE_EXTENSION);
        assertThat(actualResult).hasSize(expectedResult.size()).containsAll(expectedResult);
    }

    @Test
    @DisplayName("Выброс исключения, если путь не является директорией")
    void throwFileSystemHandlerExceptionIfPathIsNotDirectory() {
        var fileSystemHandler = new FileSystemHandler(NUMBER_OF_THREADS);
        var exception = assertThrows(
            FileSystemHandlerException.class,
            () -> fileSystemHandler.getDirectoriesWithMoreThanNthFiles(TEST_PATH.resolve("1.txt"), FILES_COUNT)
        );
        assertThat(exception.getMessage()).isEqualTo("Path is not directory");
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);

        exception = assertThrows(
            FileSystemHandlerException.class,
            () -> fileSystemHandler.getFilesWithSizeAndExtension(TEST_PATH.resolve("1.txt"), 1, FILE_EXTENSION)
        );
        assertThat(exception.getMessage()).isEqualTo("Path is not directory");
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);
    }

}
