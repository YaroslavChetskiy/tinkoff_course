package edu.hw2.task1;

import edu.hw2.task1.Expr.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class ExprTest {

    @ParameterizedTest
    @DisplayName("Тестирование вычисления (метод evaluate) Constant")
    @CsvSource(value = {
        "0.00001, 0.00001",
        "256, 256",
        "-512, -512",
        "0, 0",
    })
    void checkConstantEvaluation(double value, double expectedResult) {
        var constant = new Constant(value);
        double actualResult = constant.evaluate();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Отрицание числа, Negate")
    @MethodSource("getArgumentsForNegateEvaluationChecking")
    void checkNegateEvaluation(Expr operand, double expectedResult) {
        var negate = new Negate(operand);
        double actualResult = negate.evaluate();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForNegateEvaluationChecking() {
        return Stream.of(
            Arguments.of(
                new Constant(0), 0
            ),
            Arguments.of(
                new Constant(-100), 100
            ),
            Arguments.of(
                new Constant(256), -256
            ),
            Arguments.of(
                new Constant(0.33333), -0.33333
            ),
            Arguments.of(
                new Constant(-0.925), 0.925
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Возведение в степень, Exponent")
    @MethodSource("getArgumentsForExponentEvaluationChecking")
    void checkExponentEvaluation(Expr base, double exp, double expectedResult) {
        var exponent = new Exponent(base, exp);
        double actualResult = exponent.evaluate();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForExponentEvaluationChecking() {
        return Stream.of(
            Arguments.of(
                new Constant(0), 99, 0
            ),
            Arguments.of(
                new Constant(-10), 3, -1000
            ),
            Arguments.of(
                new Constant(16), 2, 256
            ),
            Arguments.of(
                new Constant(-2), 4, 16
            ),
            Arguments.of(
                new Constant(1.0), 25, 1.0
            ),
            Arguments.of(
                new Negate(new Constant(8)), 4, 4096
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Сложение, Addition")
    @MethodSource("getArgumentsForAdditionEvaluationChecking")
    void checkAdditionEvaluation(Expr firstValue, Expr secondValue, double expectedResult) {
        var addition = new Addition(firstValue, secondValue);
        double actualResult = addition.evaluate();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForAdditionEvaluationChecking() {
        return Stream.of(
            Arguments.of(
                new Constant(0), new Constant(100), 100
            ),
            Arguments.of(
                new Constant(-10), new Constant(10), 0
            ),
            Arguments.of(
                new Constant(0.0001), new Constant(0.0001), 0.0002
            ),
            Arguments.of(
                new Constant(-0.99), new Constant(-0.01), -1
            ),
            Arguments.of(
                new Constant(20), new Constant(-40), -20
            ),
            Arguments.of(
                new Negate(new Constant(123)), new Constant(321), 198
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Умножение, Multiplication")
    @MethodSource("getArgumentsForMultiplicationEvaluationChecking")
    void checkMultiplicationEvaluation(Expr firstValue, Expr secondValue, double expectedResult) {
        var multiplication = new Multiplication(firstValue, secondValue);
        double actualResult = multiplication.evaluate();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForMultiplicationEvaluationChecking() {
        return Stream.of(
            Arguments.of(
                new Constant(0), new Constant(100), 0
            ),
            Arguments.of(
                new Constant(-10), new Constant(10), -100
            ),
            Arguments.of(
                new Constant(0.02), new Constant(3), 0.06
            ),
            Arguments.of(
                new Constant(-1), new Constant(-2), 2
            ),
            Arguments.of(
                new Constant(16), new Constant(8), 128
            ),
            Arguments.of(
                new Negate(new Constant(111)), new Constant(6), -666
            )
        );
    }
}
