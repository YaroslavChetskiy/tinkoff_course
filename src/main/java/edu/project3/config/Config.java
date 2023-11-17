package edu.project3.config;

import edu.project3.statistics.StatsOutputFormat;
import java.time.LocalDate;

public record Config(String path, LocalDate from, LocalDate to, StatsOutputFormat format) {
}
