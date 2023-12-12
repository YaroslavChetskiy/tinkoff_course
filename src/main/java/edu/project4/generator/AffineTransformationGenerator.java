package edu.project4.generator;

import edu.project4.transformation.AffineTransformation;
import java.util.ArrayList;
import java.util.List;

public class AffineTransformationGenerator {

    private static final double MIN_VALUE = -1;
    private static final double MAX_VALUE = 1;
    private static final int COLOR_BOUND_VALUE = 256;

    private final RandomNumberGenerator numberGenerator;

    // Коэффициенты удовлетворяют условиям:
    // 1. a*a + d*d < 1
    // 2. b*b + e*e < 1
    // 3. a*a + b*b + d*d + e*e < 1 + (a*e - b*d)^2

    public AffineTransformationGenerator(RandomNumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public AffineTransformation generateAffine() {
        double a;
        double b;
        double d;
        double e;
        do {
            a = numberGenerator.getRandomDouble(MIN_VALUE, MAX_VALUE);
            b = numberGenerator.getRandomDouble(MIN_VALUE, MAX_VALUE);
            d = numberGenerator.getRandomDouble(MIN_VALUE, MAX_VALUE);
            e = numberGenerator.getRandomDouble(MIN_VALUE, MAX_VALUE);
        } while (!(a * a + d * d < 1.0
            && b * b + e * e < 1.0
            && a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d))
        );
        double c = numberGenerator.getRandomDouble(MIN_VALUE, MAX_VALUE);
        double f = numberGenerator.getRandomDouble(MIN_VALUE, MAX_VALUE);

        int red = numberGenerator.getRandomInt(0, COLOR_BOUND_VALUE);
        int green = numberGenerator.getRandomInt(0, COLOR_BOUND_VALUE);
        int blue = numberGenerator.getRandomInt(0, COLOR_BOUND_VALUE);

        return new AffineTransformation(a, b, c, d, e, f, red, green, blue);
    }

    public List<AffineTransformation> generateAffineTransformations(int affineCount) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < affineCount; i++) {
            affineTransformations.add(generateAffine());
        }
        return affineTransformations;
    }
}
