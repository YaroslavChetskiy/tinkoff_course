package edu.project3.statistics;

import java.util.Arrays;
import java.util.Optional;

public enum StatsOutputFormat {
    MARKDOWN("", "#", "|", true, "-", ".md"),
    ADOC("|====", "=", "|", false, "", ".adoc");

    final String tableMark;
    final String headerSymbol;
    final String columnSeparator;

    final boolean hasLastColumnSeparator;
    final String titleAndRowsSeparator;
    final String fileExtension;

    StatsOutputFormat(
        String tableMark,
        String headerSymbol,
        String columnSeparator,
        boolean hasLastColumnSeparator,
        String titleAndRowsSeparator,
        String fileExtension
    ) {
        this.fileExtension = fileExtension;
        this.tableMark = tableMark;
        this.headerSymbol = headerSymbol;
        this.columnSeparator = columnSeparator;
        this.hasLastColumnSeparator = hasLastColumnSeparator;
        this.titleAndRowsSeparator = titleAndRowsSeparator;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getTableMark() {
        return tableMark;
    }

    public String getHeaderSymbol() {
        return headerSymbol;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public String getTitleAndRowsSeparator() {
        return titleAndRowsSeparator;
    }

    public boolean isHasLastColumnSeparator() {
        return hasLastColumnSeparator;
    }

    public static Optional<StatsOutputFormat> find(String format) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(format))
            .findFirst();
    }
}
