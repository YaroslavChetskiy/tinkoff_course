package edu.project3.analyzer;

import edu.project3.log.LogRecord;
import edu.project3.table.Table;
import java.util.List;

public interface LogAnalyzer {

    Table analyze(List<LogRecord> logs);
}
