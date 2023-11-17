package edu.project3.analyzer;

import edu.project3.config.Config;
import edu.project3.log.LogRecord;
import edu.project3.reader.LogReader;
import edu.project3.table.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.joining;

public class GeneralLogAnalyzer implements LogAnalyzer {

    private static final String DATE_PATTERN = "dd.MM.yyyy";

    private final Config config;

    public GeneralLogAnalyzer(Config config) {
        this.config = config;
    }

    @Override
    public Table analyze(List<LogRecord> logs) {
        Table resultTable = new Table("Общая информация", List.of("Метрика", "Значение"));
        // File(s) name
        var logFiles = LogReader.getLogFiles(config.path());
        resultTable.addRow(List.of(
            "Файл(-ы)",
            logFiles.stream()
                .map(it -> it.getFileName().toString())
                .collect(joining(","))
        ));

        // Start date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        resultTable.addRow(List.of(
            "Начальная дата",
            config.from() == LocalDate.MIN ? "-" : config.from().format(formatter)
        ));

        // Final date
        resultTable.addRow(List.of(
            "Конечная дата",
            config.to() == LocalDate.MAX ? "-" : config.to().format(formatter)
        ));

        // Requests count
        resultTable.addRow(List.of(
            "Количество запросов",
            String.valueOf(logs.size())
        ));

        // Average response size in bytes
        resultTable.addRow(List.of(
            "Средний размер ответа",
            String.valueOf(logs.stream()
                .mapToLong(LogRecord::bodyBytesSent)
                .average()
                .orElse(0)
            ) + 'b'
        ));

        // NEW STATISTIC
        // COUNT OF UNIQUE VISITORS (IP + DATE + USER AGENT)

        resultTable.addRow(List.of(
            "Количество уникальных посетителей",
            String.valueOf(logs.stream()
                .map(log -> log.remoteAddress() + log.localTime().toLocalDate() + log.httpUserAgent())
                .collect(Collectors.toSet())
                .size())
        ));

        return resultTable;
    }
}
