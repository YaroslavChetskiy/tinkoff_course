package edu.hw5.task6;

import java.util.stream.Collectors;

public final class Task6 {

    private Task6() {
    }


    public static boolean isSubsequence(String seq, String string) {
        if (seq == null || string == null) {
            return false;
        }
        String subSequenceRegex = ".*" + seq.chars()
            .mapToObj(c -> (char) c + ".*")
            .collect(Collectors.joining());
        return string.matches(subSequenceRegex);
    }
}
