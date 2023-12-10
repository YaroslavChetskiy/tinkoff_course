package edu.hw10.task1.generator.argumentGenerator;

import java.lang.annotation.Annotation;

public interface ArgumentGenerator {

    Object generate(Annotation[] annotations);

}
