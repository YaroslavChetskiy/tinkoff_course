package edu.hw10.task1.generator;

import edu.hw10.task1.exception.RandomObjectGeneratorException;
import edu.hw10.task1.generator.argumentGenerator.ArgumentGenerator;
import edu.hw10.task1.generator.argumentGenerator.BooleanGenerator;
import edu.hw10.task1.generator.argumentGenerator.IntGenerator;
import edu.hw10.task1.generator.argumentGenerator.StringGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

public class RandomObjectGenerator {

    private static final Map<Class<?>, ArgumentGenerator> ARGUMENT_GENERATOR_MAP = Map.of(
        String.class, new StringGenerator(),
        int.class, new IntGenerator(),
        boolean.class, new BooleanGenerator()
    );

    public Object nextObject(Class<?> clazz) {
        var constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new RandomObjectGeneratorException(
                "Could not find any public constructor in class",
                new IllegalArgumentException()
            );
        }

        try {
            var constructor = constructors[0];
            var parameters = constructor.getParameters();
            var annotations = constructor.getParameterAnnotations();

            if (parameters.length == 0) {
                return constructor.newInstance();
            }

            return constructor.newInstance(getArguments(parameters, annotations));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RandomObjectGeneratorException("Could not create the instance", e);
        }
    }

    public Object nextObject(Class<?> clazz, String fabricMethodName) {
        var fabricMethodOptional = Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> method.getName().equals(fabricMethodName)
                && Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()))
            .findFirst();
        if (fabricMethodOptional.isEmpty()) {
            throw new RandomObjectGeneratorException(
                "Could not find any public fabric method with name " + fabricMethodName + " in class",
                new IllegalArgumentException()
            );
        }
        try {
            var fabricMethod = fabricMethodOptional.get();
            var parameters = fabricMethod.getParameters();
            var annotations = fabricMethod.getParameterAnnotations();

            if (parameters.length == 0) {
                return fabricMethod.invoke(null);
            }

            var arguments = getArguments(parameters, annotations);
            return fabricMethod.invoke(null, arguments);

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RandomObjectGeneratorException("Could not invoke fabric method", e);
        }
    }

    private Object[] getArguments(Parameter[] parameters, Annotation[][] annotations) {
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameters[i].getType();
            args[i] = ARGUMENT_GENERATOR_MAP.get(parameterType).generate(annotations[i]);
        }
        return args;
    }

}
