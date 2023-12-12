package edu.hw10.task1.entities;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;

public record MyRecord(@NotNull String name, @Max(100) @Min(0) int age, boolean hasJob) {
}
