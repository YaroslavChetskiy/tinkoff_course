package edu.hw10.task1.generator.argumentGenerator;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class IntGenerator implements ArgumentGenerator {

    private static final long DEFAULT_RIGHT_BOUND = Integer.MAX_VALUE;
    private static final long DEFAULT_LEFT_BOUND = Integer.MIN_VALUE;

    @Override
    public Object generate(Annotation[] annotations) {
        var maxAnnotation = Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType().equals(Max.class))
            .findFirst();
        var minAnnotation = Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType().equals(Min.class))
            .findFirst();

        var rightBound = maxAnnotation
            .map(annotation -> ((Max) annotation).value())
            .orElse(DEFAULT_RIGHT_BOUND);

        var leftBound = minAnnotation
            .map(annotation -> ((Min) annotation).value())
            .orElse(DEFAULT_LEFT_BOUND);

        return ThreadLocalRandom.current().nextInt(leftBound.intValue(), rightBound.intValue());
    }
}
