package edu.project3.reader;

import edu.project3.exception.LogReaderException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogReaderTest {

    private static final Path LOG_PATH = Path.of("src/test/resources/project3/logs/log1.txt");

    private static final String LOG_URL =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
    public static final Path NGINX_LOGS_PATH = Path.of("src", "main", "resources", "logs", "nginx_logs.txt");

    @Test
    @DisplayName("Получение списка логов по URL/LOCAL_FILE пути")
    void getLogListByPath() throws IOException {
        var expectedResult = Files.readAllLines(LOG_PATH);
        var actualResult = LogReader.getLogListByPath(LOG_PATH.toString());
        assertThat(actualResult).isEqualTo(expectedResult);

        expectedResult = Files.readAllLines(NGINX_LOGS_PATH);
        actualResult = LogReader.getLogListByPath(LOG_URL);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неправильном пути")
    @ValueSource(strings = {
        "src/not_existed_file.txt",
        "https://githudlfkfdlkfldkdlb.com/not_existed_url",
        "",
        "dir/**/log*.txt",
        "dir/log.txt"
    })
    void throwLogReaderExceptionIfPathIsInvalid(String path) {
        assertThrows(LogReaderException.class, () -> LogReader.getLogListByPath(path));
    }

    @ParameterizedTest
    @DisplayName("Получение списка всех файлов по пути")
    @MethodSource("getArgumentsForGetAllLogFilesByPathTest")
    void getAllLogFilesByPath(String path, List<Path> expectedResult) {
        var actualResult = LogReader.getLogFiles(path);
        assertThat(actualResult).hasSize(expectedResult.size());
        assertThat(actualResult).containsAll(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetAllLogFilesByPathTest() {
        return Stream.of(
            Arguments.of(
                "src/test/resources/**/log*.txt",
                List.of(
                    LOG_PATH,
                    LOG_PATH.getParent().resolve("log2.txt"),
                    LOG_PATH.getParent().resolve("another_logs/logA.txt")
                )
            ),
            Arguments.of(
                "src/test/resources/**/logs/log*.txt",
                List.of(
                    LOG_PATH,
                    LOG_PATH.getParent().resolve("log2.txt")
                )
            ),
            Arguments.of(
                "src/test/resources/project3/logs/log*.txt",
                List.of(
                    LOG_PATH,
                    LOG_PATH.getParent().resolve("log2.txt")
                )
            ),
            Arguments.of(
                "src/test/resources/project3/logs/log1.txt",
                List.of(
                    LOG_PATH
                )
            )
        );
    }
}
