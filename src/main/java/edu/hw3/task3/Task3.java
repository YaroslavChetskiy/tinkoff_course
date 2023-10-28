package edu.hw3.task3;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Task3 {

    private Task3() {
    }

    public static <T> Map<T, Integer> getFrequencyDictionary(List<T> inputList) {
        return inputList == null
            ? Map.of()
            : inputList.stream()
            .collect(Collectors.toMap(
                    key -> key,
                    val -> 1,
                    Integer::sum
                )
            );
    }
}
