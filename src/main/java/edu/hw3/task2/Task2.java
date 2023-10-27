package edu.hw3.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class Task2 {

    private Task2() {
    }

    public static List<String> clusterBrackets(String inputString) {
        if (inputString == null) {
            return List.of();
        }
        List<String> resultList = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        var currentCluster = new StringBuilder();
        for (char c : inputString.toCharArray()) {
            if (c == ')' && stack.isEmpty()) {
                // так как нельзя разбить на сбалансированные кластеры
                return List.of();
            }
            currentCluster.append(c);
            if (c == '(') {
                stack.add(c);
            } else if (c == ')') {
                stack.pop();
                if (stack.isEmpty()) {
                    resultList.add(currentCluster.toString());
                    currentCluster.setLength(0);
                }
            }
        }
        return resultList;
    }
}
