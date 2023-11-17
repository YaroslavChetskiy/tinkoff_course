package edu.project3;

import edu.project3.analyzer.GeneralLogAnalyzer;
import edu.project3.analyzer.LogAnalyzer;
import edu.project3.analyzer.ResourceLogAnalyzer;
import edu.project3.analyzer.StatusLogAnalyzer;
import edu.project3.analyzer.VisitTimesLogAnalyzer;
import edu.project3.parser.ArgumentParser;
import edu.project3.parser.LogParser;
import edu.project3.reportMaker.ReportMaker;
import edu.project3.statistics.StatsOutputFormat;
import java.nio.file.Path;
import java.util.List;
import static edu.project3.reader.LogReader.getLogListByPath;

public final class Main {

    private static final Path DEFAULT_REPORTS_PATH = Path.of("src", "main", "resources", "reports");

    private Main() {
    }

    public static void main(String[] args) {
        var config = ArgumentParser.parse(args);

        var logListByPath = LogParser.parseAll(getLogListByPath(config.path()))
            .stream()
            .filter(log -> log.isBetweenDates(config.from(), config.to()))
            .toList();

        List<LogAnalyzer> logAnalyzers = List.of(
            new GeneralLogAnalyzer(config),
            new ResourceLogAnalyzer(),
            new StatusLogAnalyzer(),
            new VisitTimesLogAnalyzer()
        );

        var tables = logAnalyzers.stream()
            .map(it -> it.analyze(logListByPath))
            .toList();

        StatsOutputFormat format = config.format();
        ReportMaker reportMaker = new ReportMaker(format);
        reportMaker.makeReport(DEFAULT_REPORTS_PATH.resolve("report" + format.getFileExtension()), tables);
    }
}
