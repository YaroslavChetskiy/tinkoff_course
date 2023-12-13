package edu.hw11.task3.implementation;

import edu.hw11.task3.appender.FibMethod;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import org.jetbrains.annotations.NotNull;

public enum FibonacciCalculatorImplementation implements Implementation {

    INSTANCE;

    @Override
    public @NotNull ByteCodeAppender appender(@NotNull Target target) {
        return FibMethod.INSTANCE;
    }

    @Override
    public @NotNull InstrumentedType prepare(@NotNull InstrumentedType instrumentedType) {
        return instrumentedType;
    }
}
