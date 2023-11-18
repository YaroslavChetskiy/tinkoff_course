package edu.project3.reportMaker;

import edu.project3.analyzer.GeneralLogAnalyzer;
import edu.project3.analyzer.LogAnalyzer;
import edu.project3.analyzer.ResourceLogAnalyzer;
import edu.project3.analyzer.StatusLogAnalyzer;
import edu.project3.analyzer.VisitTimesLogAnalyzer;
import edu.project3.config.Config;
import edu.project3.exception.ReportMakerException;
import edu.project3.parser.LogParser;
import edu.project3.statistics.StatsOutputFormat;
import edu.project3.table.Table;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project3.reader.LogReader.getLogListByPath;
import static edu.project3.statistics.StatsOutputFormat.ADOC;
import static edu.project3.statistics.StatsOutputFormat.MARKDOWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReportMakerTest {

    private static final Path LOGS_PATH = Path.of("src", "test", "resources", "project3", "logs");

    @Test
    @DisplayName("Выброс исключения при неправильном пути к файлу")
    void throwReportMakerExceptionIfPathIsInvalid() {
        var wrongMarkdownPath = Path.of("wrong", "path.md");
        var wrongAdocPath = Path.of("wrong", "path.adoc");
        var markdownReportMaker = new ReportMaker(MARKDOWN);
        var adocReportMaker = new ReportMaker(StatsOutputFormat.ADOC);
        assertAll(
            () -> assertThrows(
                ReportMakerException.class,
                () -> markdownReportMaker.makeReport(wrongMarkdownPath, List.of())
            ),
            () -> assertThrows(ReportMakerException.class, () -> adocReportMaker.makeReport(wrongAdocPath, List.of()))
        );
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неправильном расширении файла")
    @MethodSource("getArgumentsForInvalidFileExtensionTest")
    void throwReportMakerExceptionIfFileHasInvalidExtension(Path path, StatsOutputFormat format) {
        var reportMaker = new ReportMaker(format);
        var exception =
            assertThrows(ReportMakerException.class, () -> reportMaker.makeReport(path, List.of()));
        assertThat(exception.getMessage()).isEqualTo("Invalid report file extension");
    }

    static Stream<Arguments> getArgumentsForInvalidFileExtensionTest() {
        return Stream.of(
            Arguments.of(Path.of("src", "report.adoc"), MARKDOWN),
            Arguments.of(Path.of("src", "report.md"), ADOC),
            Arguments.of(Path.of("src", "report.txt"), MARKDOWN),
            Arguments.of(Path.of("src", "report.xml"), ADOC),
            Arguments.of(Path.of("src", "report"), MARKDOWN)
        );
    }

    @ParameterizedTest
    @DisplayName("Создание отчёта")
    @MethodSource("getArgumentsForMakeCorrectReportFileTest")
    void makeCorrectReportFile(Path path, List<Table> tables, StatsOutputFormat format, Path expectedReportPath)
        throws IOException {
        var reportMaker = new ReportMaker(format);
        reportMaker.makeReport(path, tables);

        assertThat(Files.exists(path)).isTrue();

        var expectedResult = Files.readAllLines(expectedReportPath);
        var actualResult = Files.readAllLines(path);

        assertThat(actualResult).isEqualTo(expectedResult);

        Files.delete(path);
    }

    static Stream<Arguments> getArgumentsForMakeCorrectReportFileTest() {
        return Stream.of(
            Arguments.of(
                LOGS_PATH.resolve("test_report.md"),
                prepareTables(MARKDOWN),
                MARKDOWN,
                LOGS_PATH.resolve("expected_report.md")
            ),
            Arguments.of(
                LOGS_PATH.resolve("test_report.adoc"),
                prepareTables(ADOC),
                ADOC,
                LOGS_PATH.resolve("expected_report.adoc")
            )
        );
    }

    private static List<Table> prepareTables(StatsOutputFormat format) {
        List<Table> resultTables = new ArrayList<>();
        var logPath = LOGS_PATH.resolve("log2.txt").toString();
        var logRecords = LogParser.parseAll(getLogListByPath(logPath));
        var logAnalyzers = List.of(
            new GeneralLogAnalyzer(new Config(logPath, LocalDate.MIN, LocalDate.MAX, format)),
            new ResourceLogAnalyzer(),
            new StatusLogAnalyzer(),
            new VisitTimesLogAnalyzer()
        );
        for (LogAnalyzer logAnalyzer : logAnalyzers) {
            resultTables.add(logAnalyzer.analyze(logRecords));
        }
        return resultTables;
    }
}
