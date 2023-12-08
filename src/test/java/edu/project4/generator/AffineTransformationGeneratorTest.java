package edu.project4.generator;

import edu.project4.transformation.AffineTransformation;
import java.util.concurrent.ThreadLocalRandom;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AffineTransformationGeneratorTest {

    private static final int COLOR_BOUND = 256;

    private static final int TEST_COUNT = 10;

    @Test
    @DisplayName("Генерация нескольких аффинных преобразований")
    void getAffineTransformations() {
        var generator = new AffineTransformationGenerator(new RandomNumberGenerator(ThreadLocalRandom.current()));
        var actualResult = generator.generateAffineTransformations(TEST_COUNT);
        for (AffineTransformation affineTransformation : actualResult) {
            Assertions.assertThat(affineTransformation.getRed()).isBetween(0, COLOR_BOUND);
            Assertions.assertThat(affineTransformation.getGreen()).isBetween(0, COLOR_BOUND);
            Assertions.assertThat(affineTransformation.getBlue()).isBetween(0, COLOR_BOUND);
        }
    }

}
