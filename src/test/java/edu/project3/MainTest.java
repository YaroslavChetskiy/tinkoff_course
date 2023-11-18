package edu.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    private static final Path CREATED_REPORT_PATH = Path.of("src", "main", "resources", "reports", "report.md");
    public static final Path LOGS_PATH = Path.of("src/test/resources/project3/logs");

    @Test
    @DisplayName("Тестирование всего приложения")
    void getCorrectStatisticsReport() throws IOException {
        var path = LOGS_PATH.resolve("log2.txt").toString();
        Main.main(new String[] {"--path", path, "--format", "markdown"});
        assertThat(Files.exists(CREATED_REPORT_PATH)).isTrue();

        var expectedResult = Files.readAllLines(LOGS_PATH.resolve("expected_report.md"));
        var actualResult = Files.readAllLines(CREATED_REPORT_PATH);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}
