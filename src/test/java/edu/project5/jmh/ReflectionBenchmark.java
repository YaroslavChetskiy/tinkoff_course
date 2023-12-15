package edu.project5.jmh;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
/*
SETTINGS:
FORKS: 2 | WARMUP_FORKS: 1 | WARMUP_ITERATIONS: 5
WARMUP_TIME: 10 SEC | MEASUREMENT_ITERATIONS: 5 | MEASUREMENT_TIME: 10 SEC
RESULTS:
Benchmark                                    Mode  Cnt  Score   Error  Units
ReflectionBenchmark.directAccess             avgt   10  0,876 ± 0,022  ns/op
ReflectionBenchmark.lambdaMetafactoryAccess  avgt   10  1,057 ± 0,007  ns/op
ReflectionBenchmark.methodHandleAccess       avgt   10  4,487 ± 0,014  ns/op
ReflectionBenchmark.methodReflectionAccess   avgt   10  8,415 ± 0,050  ns/op
BENCHMARK EXECUTION TIME: ~20 MINUTES
 */

@State(Scope.Thread)
public class ReflectionBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }

    record Student(String name, String surname) {
    }

    private Student student;
    private Method method;

    private MethodHandle methodHandle;

    private Function<Student, String> lambdaMetafactoryFunction;

    @Setup
    @SuppressWarnings("unchecked")
    public void setup() throws Throwable {
        student = new Student("Alexander", "Biryukov");
        method = student.getClass().getMethod("name");
        var caller = MethodHandles.lookup();
        methodHandle = caller.findVirtual(Student.class, "name", MethodType.methodType(String.class));
        var lambdaMetafactory = LambdaMetafactory.metafactory(
            caller,
            "apply",
            MethodType.methodType(Function.class),
            MethodType.methodType(Object.class, Object.class),
            methodHandle,
            methodHandle.type()
        );
        lambdaMetafactoryFunction = (Function<Student, String>) lambdaMetafactory.getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void methodReflectionAccess(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandleAccess(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactoryAccess(Blackhole bh) {
        String name = lambdaMetafactoryFunction.apply(student);
        bh.consume(name);
    }
}
