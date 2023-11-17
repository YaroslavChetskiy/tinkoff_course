package edu.project3.analyzer;

import edu.project3.config.Config;
import edu.project3.log.LogRecord;
import edu.project3.parser.LogParser;
import edu.project3.table.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project3.reader.LogReader.getLogListByPath;
import static edu.project3.statistics.StatsOutputFormat.ADOC;
import static org.assertj.core.api.Assertions.assertThat;

class LogAnalyzerTest {

    public static final String LOG_PATH = "src/test/resources/project3/logs/log2.txt";

    private static final List<LogRecord> LOGS_LIST = LogParser.parseAll(getLogListByPath(LOG_PATH));

    private static final List<List<String>> GENERAL_ROWS = List.of(
        List.of("Файл(-ы)", "log2.txt"),
        List.of("Начальная дата", "-"),
        List.of("Конечная дата", "17.11.2023"),
        List.of("Количество запросов", "3"),
        List.of("Средний размер ответа", "2.0b"),
        List.of("Количество уникальных посетителей", "2")
    );

    private static final List<List<String>> RESOURCES_ROWS = List.of(
        List.of("/downloads/product_1", "2"),
        List.of("/downloads/product_2", "1")
    );

    private static final List<List<String>> STATUS_ROWS = List.of(
        List.of("404", "Not Found", "2"),
        List.of("503", "Service Unavailable", "1")
    );

    private static final List<List<String>> VISIT_TIMES_ROWS = List.of(
        List.of("00:00 - 01:00", "0"),
        List.of("01:00 - 02:00", "0"),
        List.of("02:00 - 03:00", "0"),
        List.of("03:00 - 04:00", "0"),
        List.of("04:00 - 05:00", "0"),
        List.of("05:00 - 06:00", "0"),
        List.of("06:00 - 07:00", "0"),
        List.of("07:00 - 08:00", "1"),
        List.of("08:00 - 09:00", "1"),
        List.of("09:00 - 10:00", "0"),
        List.of("10:00 - 11:00", "0"),
        List.of("11:00 - 12:00", "0"),
        List.of("12:00 - 13:00", "0"),
        List.of("13:00 - 14:00", "0"),
        List.of("14:00 - 15:00", "0"),
        List.of("15:00 - 16:00", "0"),
        List.of("16:00 - 17:00", "0"),
        List.of("17:00 - 18:00", "0"),
        List.of("18:00 - 19:00", "0"),
        List.of("19:00 - 20:00", "0"),
        List.of("20:00 - 21:00", "0"),
        List.of("21:00 - 22:00", "0"),
        List.of("22:00 - 23:00", "0"),
        List.of("23:00 - 00:00", "1")
    );

    @Test
    @DisplayName("Получение таблицы с общей информацией")
    void getTableWithGeneralInformation() {
        var expectedResult = new Table("Общая информация", List.of("Метрика", "Значение"), GENERAL_ROWS);
        var config = new Config(LOG_PATH, LocalDate.MIN, LocalDate.of(2023, 11, 17), ADOC);
        var logAnalyzer = new GeneralLogAnalyzer(config);
        var actualResult = logAnalyzer.analyze(LOGS_LIST);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Получение таблицы с дополнительной информацией")
    @MethodSource("getArgumentsForGetTableWithExtraInformationTest")
    void getTableWithExtraInformation(LogAnalyzer logAnalyzer, Table expectedResult) {
        var actualResult = logAnalyzer.analyze(LOGS_LIST);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetTableWithExtraInformationTest() {
        return Stream.of(
            Arguments.of(
                new ResourceLogAnalyzer(),
                new Table("Запрашиваемые ресурсы", List.of("Ресурс", "Количество"), RESOURCES_ROWS)
            ),
            Arguments.of(
                new StatusLogAnalyzer(),
                new Table("Коды ответа", List.of("Код", "Имя", "Количество"), STATUS_ROWS)
            ),
            Arguments.of(
                new VisitTimesLogAnalyzer(),
                new Table("Почасовой отчёт", List.of("Час", "Количество"), VISIT_TIMES_ROWS)
            )
        );
    }
}
