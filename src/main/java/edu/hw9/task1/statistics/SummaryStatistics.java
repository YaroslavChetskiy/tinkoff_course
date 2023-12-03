package edu.hw9.task1.statistics;

public record SummaryStatistics<T extends Number>(Long count, T min, T max, T sum, T average) {
}
