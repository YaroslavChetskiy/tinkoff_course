package edu.hw2.task3.generator;

import java.util.Random;

public class IntegerGenerator implements RandomValueGenerator<Integer> {

    private static final Random RANDOM = new Random();

    @Override
    public Integer getRandomValue(Integer leftBound, Integer rightBound) {
        return RANDOM.nextInt(leftBound, rightBound);
    }
}
