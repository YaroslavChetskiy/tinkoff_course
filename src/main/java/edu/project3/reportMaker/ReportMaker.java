package edu.project3.reportMaker;

import edu.project3.exception.ReportMakerException;
import edu.project3.statistics.StatsOutputFormat;
import edu.project3.table.Table;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class ReportMaker {

    private static final int START_END_SPACES_COUNT = 2;

    private static final int SECOND_HEADER_LEVEL = 2;
    private static final int THIRD_HEADER_LEVEL = 3;
    private static final String SPACE = " ";

    private final StatsOutputFormat format;

    public ReportMaker(StatsOutputFormat format) {
        this.format = format;
    }

    public void makeReport(Path path, List<Table> tables) {
        validatePath(path.toString());
        try {
            Files.newBufferedWriter(path, CREATE, TRUNCATE_EXISTING)
                .close();
            Files.write(path, makeHeaderLine().getBytes(), APPEND);
            for (Table table : tables) {
                Files.write(path, makeTableLines(table), APPEND);
                Files.write(path, ("\n").getBytes(), APPEND);
            }
        } catch (IOException e) {
            throw new ReportMakerException("Could not create report file in " + path, e);
        }
    }

    private void validatePath(String path) {
        if (!path.endsWith(format.getFileExtension())) {
            throw new ReportMakerException("Invalid report file extension");
        }
    }

    private String makeHeaderLine() {
        return format.getHeaderSymbol().repeat(SECOND_HEADER_LEVEL)
            + " Log statistics report : "
            + LocalDate.now() + "\n\n";
    }

    private List<String> makeTableLines(Table table) {
        List<String> lines = new ArrayList<>();

        lines.add(format.getHeaderSymbol().repeat(THIRD_HEADER_LEVEL) + SPACE + table.header());

        var tableMark = format.getTableMark();
        var titleAndRowsSeparator = format.getTitleAndRowsSeparator();
        if (!tableMark.isBlank()) {
            lines.add(tableMark);
        }

        List<Integer> columnSizes = getColumnSizes(table);
        lines.add(makeRowLine(table.titles(), columnSizes));

        if (!titleAndRowsSeparator.isBlank()) {
            var separateRow = columnSizes.stream()
                .map(it -> titleAndRowsSeparator.repeat(it - START_END_SPACES_COUNT))
                .toList();
            lines.add(makeRowLine(separateRow, columnSizes));
        }

        for (List<String> row : table.rows()) {
            lines.add(makeRowLine(row, columnSizes));
        }

        if (!tableMark.isBlank()) {
            lines.add(tableMark);
        }
        return lines;
    }

    private List<Integer> getColumnSizes(Table table) {
        List<Integer> sizes = new ArrayList<>();
        for (int i = 0; i < table.titles().size(); i++) {
            int size = table.titles().get(i).length();
            for (List<String> row : table.rows()) {
                size = Math.max(size, row.get(i).length());
            }
            sizes.add(size + START_END_SPACES_COUNT);
        }
        return sizes;
    }

    private String makeRowLine(List<String> row, List<Integer> columnSizes) {
        StringBuilder sb = new StringBuilder();
        var columnSeparator = format.getColumnSeparator();
        for (int i = 0; i < row.size(); i++) {
            sb.append(columnSeparator);
            var spacesCount = columnSizes.get(i) - row.get(i).length();
            int toLeft = spacesCount / 2;
            int toRight = spacesCount - toLeft;
            sb.append(SPACE.repeat(toLeft)).append(row.get(i)).append(SPACE.repeat(toRight));
        }
        if (format.isHasLastColumnSeparator()) {
            sb.append(columnSeparator);
        }
        return sb.toString();
    }
}
