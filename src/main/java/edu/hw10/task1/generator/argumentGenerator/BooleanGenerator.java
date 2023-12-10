package edu.hw10.task1.generator.argumentGenerator;

import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class BooleanGenerator implements ArgumentGenerator {

    @Override
    public Object generate(Annotation[] annotations) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
