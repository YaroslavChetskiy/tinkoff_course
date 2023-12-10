package edu.hw10.task1.generator.argumentGenerator;

import edu.hw10.task1.annotations.NotNull;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class StringGenerator implements ArgumentGenerator {

    private static final int STRING_MAX_LENGTH = 10;
    private static final int ALPHABET_SIZE = 26;

    @Override
    public Object generate(Annotation[] annotations) {
        var notNull = Arrays.stream(annotations)
            .filter(annotation -> annotation.annotationType().equals(NotNull.class))
            .findFirst();

        if (notNull.isPresent()) {
            return generateString();
        }

        return null;
    }

    private String generateString() {
        int length = ThreadLocalRandom.current().nextInt(STRING_MAX_LENGTH);
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char symbol = (char) (ThreadLocalRandom.current().nextInt(ALPHABET_SIZE) + 'a');
            sb.append(symbol);
        }
        return sb.toString();
    }
}
