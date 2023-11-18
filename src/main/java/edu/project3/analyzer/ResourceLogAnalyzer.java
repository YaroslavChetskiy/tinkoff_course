package edu.project3.analyzer;

import edu.project3.log.LogRecord;
import edu.project3.log.RequestRecord;
import edu.project3.table.Table;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ResourceLogAnalyzer implements LogAnalyzer {

    @Override
    public Table analyze(List<LogRecord> logs) {
        Table resultTable = new Table("Запрашиваемые ресурсы", List.of("Ресурс", "Количество"));

        var countMap = logs.stream()
            .map(LogRecord::request)
            .collect(groupingBy(RequestRecord::resource, counting()));
        countMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(entry -> resultTable.addRow(
                List.of(entry.getKey(), String.valueOf(entry.getValue()))
            ));

        return resultTable;
    }
}
