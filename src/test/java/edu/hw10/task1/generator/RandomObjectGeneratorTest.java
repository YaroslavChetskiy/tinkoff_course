package edu.hw10.task1.generator;

import edu.hw10.task1.entities.MyClass;
import edu.hw10.task1.entities.MyRecord;
import edu.hw10.task1.entities.MySecondRecord;
import edu.hw10.task1.entities.MyUtilClass;
import edu.hw10.task1.exception.RandomObjectGeneratorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw10.task1.entities.MyClass.MAX_AGE;
import static edu.hw10.task1.entities.MyClass.MIN_AGE;
import static edu.hw10.task1.entities.MySecondRecord.DEFAULT_AGE;
import static edu.hw10.task1.entities.MySecondRecord.DEFAULT_HAS_JOB;
import static edu.hw10.task1.entities.MySecondRecord.DEFAULT_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomObjectGeneratorTest {
    private static final int DEFAULT_RIGHT_BOUND = Integer.MAX_VALUE;
    private static final int DEFAULT_LEFT_BOUND = Integer.MIN_VALUE;
    private static final String FABRIC_METHOD_NAME = "create";

    @Test
    @DisplayName("Генерация объекта через конструктор без параметров")
    void generateObjectUsingDefaultConstructor() {
        var generator = new RandomObjectGenerator();
        var actualResult = generator.nextObject(MySecondRecord.class);
        assertThat(actualResult).isInstanceOf(MySecondRecord.class);
        var instance = (MySecondRecord) actualResult;
        assertThat(instance.name()).isEqualTo(DEFAULT_NAME);
        assertThat(instance.age()).isEqualTo(DEFAULT_AGE);
        assertThat(instance.hasJob()).isEqualTo(DEFAULT_HAS_JOB);
    }

    @Test
    @DisplayName("Генерация объекта через конструктор с параметрами и аннотациями")
    void generateObjectUsingConstructorWithParametersAndAnnotations() {
        // MyClass:
        var generator = new RandomObjectGenerator();
        var actualResult = generator.nextObject(MyClass.class);
        assertThat(actualResult).isInstanceOf(MyClass.class);
        var classInstance = (MyClass) actualResult;
        assertThat(classInstance.getName()).isNotNull();
        assertThat(classInstance.getSurName()).isNull();
        assertThat(classInstance.getAge()).isBetween(MIN_AGE, MAX_AGE);
        assertThat(classInstance.getHeight()).isBetween(DEFAULT_LEFT_BOUND, DEFAULT_RIGHT_BOUND);

        // MyRecord:
        actualResult = generator.nextObject(MyRecord.class);
        assertThat(actualResult).isInstanceOf(MyRecord.class);
        var recordInstance = (MyRecord) actualResult;
        assertThat(recordInstance.name()).isNotNull();
        assertThat(recordInstance.age()).isBetween(MIN_AGE, MAX_AGE);
    }

    @Test
    @DisplayName("Генерация объекта через фабричный метод")
    void generateObjectUsingFabricMethod() {
        // MyClass:
        var generator = new RandomObjectGenerator();
        var actualResult = generator.nextObject(MyClass.class, FABRIC_METHOD_NAME);
        assertThat(actualResult).isInstanceOf(MyClass.class);
        var classInstance = (MyClass) actualResult;
        assertThat(classInstance.getName()).isNotNull();
        assertThat(classInstance.getSurName()).isNull();
        assertThat(classInstance.getAge()).isEqualTo(MIN_AGE);
        assertThat(classInstance.getHeight()).isBetween(DEFAULT_LEFT_BOUND, DEFAULT_RIGHT_BOUND);
        assertThat(classInstance.isHasJob()).isFalse();

        // MySecondRecord:
        actualResult = generator.nextObject(MySecondRecord.class, FABRIC_METHOD_NAME);
        assertThat(actualResult).isInstanceOf(MySecondRecord.class);
        var instance = (MySecondRecord) actualResult;
        assertThat(instance.name()).isEqualTo(DEFAULT_NAME);
        assertThat(instance.age()).isEqualTo(DEFAULT_AGE);
        assertThat(instance.hasJob()).isEqualTo(DEFAULT_HAS_JOB);
    }

    @Test
    @DisplayName("Выброс исключения, если не найден ни один публичный конструктор")
    void throwRandomObjectGeneratorExceptionIfPublicConstructorNotFound() {
        var generator = new RandomObjectGenerator();
        var exception =
            assertThrows(RandomObjectGeneratorException.class, () -> generator.nextObject(MyUtilClass.class));
        assertThat(exception.getMessage()).isEqualTo("Could not find any public constructor in class");
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Выброс исключения, если не найден публичный фабричный метод")
    void throwRandomObjectGeneratorExceptionIfFabricMethodNotFound() {
        var generator = new RandomObjectGenerator();
        var exception =
            assertThrows(
                RandomObjectGeneratorException.class,
                () -> generator.nextObject(MyRecord.class, FABRIC_METHOD_NAME)
            );
        assertThat(exception.getMessage())
            .isEqualTo("Could not find any public fabric method with name " + FABRIC_METHOD_NAME + " in class");
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);
    }
}
