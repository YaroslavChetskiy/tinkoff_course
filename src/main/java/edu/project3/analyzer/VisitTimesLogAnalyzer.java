package edu.project3.analyzer;

import edu.project3.log.LogRecord;
import edu.project3.table.Table;
import java.time.LocalTime;
import java.util.List;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class VisitTimesLogAnalyzer implements LogAnalyzer {
    @Override
    public Table analyze(List<LogRecord> logs) {
        Table resultTable = new Table("Почасовой отчёт", List.of("Час", "Количество"));

        var countMap = logs.stream()
            .collect(groupingBy(
                log -> log.localTime().getHour(),
                counting()
            ));
        for (int i = 0; i <= LocalTime.MAX.getHour(); i++) {
            resultTable.addRow(List.of(
                "%02d:00 - %02d:00".formatted(i, i != LocalTime.MAX.getHour() ? i + 1 : LocalTime.MIN.getHour()),
                String.valueOf(countMap.getOrDefault(i, 0L))
            ));
        }

        return resultTable;
    }
}
