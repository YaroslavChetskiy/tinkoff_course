package edu.hw11;

import edu.hw11.task2.ArithmeticUtils;
import edu.hw11.task2.ArithmeticUtilsInterceptor;
import edu.hw11.task3.calculator.FibonacciCalculator;
import edu.hw11.task3.implementation.FibonacciCalculatorImplementation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.isToString;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteBuddyTest {

    private static final String TEST_STRING = "Hello, ByteBuddy!";
    private static final int TEST_NUMBER = 10;

    // Task 1
    @Test
    void getHelloByteBuddyInToStringMethod()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(isToString())
            .intercept(FixedValue.value(TEST_STRING))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        var actualResult = dynamicType.getDeclaredConstructor().newInstance().toString();
        assertThat(actualResult).isEqualTo(TEST_STRING);
    }

    // Task 2

    @Test
    void interceptArithmeticUtilsMethodAndDelegateToInterceptorMethod()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends ArithmeticUtils> dynamicType = new ByteBuddy()
            .subclass(ArithmeticUtils.class)
            .method(named("sum"))
            .intercept(MethodDelegation.to(ArithmeticUtilsInterceptor.class))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        int expectedResult = ArithmeticUtilsInterceptor.multiply(TEST_NUMBER, TEST_NUMBER);
        int actualResult = dynamicType.getDeclaredConstructor().newInstance().sum(TEST_NUMBER, TEST_NUMBER);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 3

    @Test
    void createClassWithDefinedFibMethodUsingASM()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Long> fibonacciNumbers = List.of(1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L);
        Class<? extends FibonacciCalculator> dynamicType = new ByteBuddy()
            .subclass(FibonacciCalculator.class)
            .method(named("fib"))
            .intercept(FibonacciCalculatorImplementation.INSTANCE)
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
        for (int i = 0; i < fibonacciNumbers.size(); i++) {
            var actualResult = dynamicType
                .getDeclaredConstructor()
                .newInstance()
                .fib(i + 1);
            assertThat(actualResult).isEqualTo(fibonacciNumbers.get(i));
        }
    }

}
