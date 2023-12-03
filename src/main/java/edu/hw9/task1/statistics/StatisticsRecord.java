package edu.hw9.task1.statistics;

import java.util.List;

public record StatisticsRecord(String metricName, List<Double> values, SummaryStatistics<Double> statistics) {
}
