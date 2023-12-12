package edu.project4.generator;

import java.util.Random;

public class RandomNumberGenerator {

    private final Random random;

    public RandomNumberGenerator(Random random) {
        this.random = random;
    }

    public double getRandomDouble(double leftBound, double rightBound) {
        return leftBound + (rightBound - leftBound) * random.nextDouble();
    }

    public int getRandomInt(int leftBound, int rightBound) {
        return random.nextInt(leftBound, rightBound);
    }
}
