package edu.project2.generator;

import java.util.Random;

public class IntegerGenerator implements RandomValueGenerator<Integer> {

    private final Random random;

    public IntegerGenerator() {
        random = new Random();
    }

    public IntegerGenerator(long seed) {
        random = new Random(seed);
    }

    @Override
    public Integer getRandomValue(Integer leftBound, Integer rightBound) {
        return random.nextInt(leftBound, rightBound);
    }
}
