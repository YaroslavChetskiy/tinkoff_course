package edu.project3.parser;

import edu.project3.exception.LogParserException;
import edu.project3.log.LogRecord;
import edu.project3.log.RequestRecord;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogParserTest {

    private static final List<String> TEST_LOGS = List.of(
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
        "93.180.72.3 - - [17/May/2015:09:05:32 +0000] \"POST /downloads/product_1 HTTP/2.0\" 307 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\""
    );

    private static final String BAD_LOG =
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"NOT_EXISTED /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";
    private static final LogRecord FIRST_LOG_RECORD = new LogRecord(
        "93.180.71.3",
        "-",
        OffsetDateTime.of(2015, 5, 17, 8, 5, 32, 0, ZoneOffset.of("+0000")),
        new RequestRecord("GET", "/downloads/product_1", "HTTP/1.1"),
        304,
        0,
        "\"-\"",
        "\"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\""
    );

    private static final LogRecord SECOND_LOG_RECORD = new LogRecord(
        "93.180.72.3",
        "-",
        OffsetDateTime.of(2015, 5, 17, 9, 5, 32, 0, ZoneOffset.of("+0000")),
        new RequestRecord("POST", "/downloads/product_1", "HTTP/2.0"),
        307,
        0,
        "\"-\"",
        "\"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\""
    );

    @Test
    @DisplayName("Получение правильного представления лога")
    void parseIfLogIsValid() {
        var actualResult = LogParser.parse(TEST_LOGS.get(0));
        assertThat(actualResult).isEqualTo(FIRST_LOG_RECORD);
    }

    @Test
    @DisplayName("Получить правильные представления нескольких логов")
    void parseAllIfLogsAreValid() {
        var actualResult = LogParser.parseAll(TEST_LOGS);
        var expectedResult = List.of(FIRST_LOG_RECORD, SECOND_LOG_RECORD);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Выброс исключения при неверном формате лога")
    void throwLogParserExceptionIfLogFormatIsInvalid() {
        assertThrows(LogParserException.class, () -> LogParser.parse(BAD_LOG));
    }
}
