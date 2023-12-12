package edu.hw11.task3.appender;

import edu.hw11.task3.stackManipulation.LongFib;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.MethodVisitor;
import org.jetbrains.annotations.NotNull;

public enum FibMethod implements ByteCodeAppender {

    INSTANCE;

    @Override
    public @NotNull Size apply(
        @NotNull MethodVisitor methodVisitor,
        Implementation.@NotNull Context implementationContext,
        MethodDescription instrumentedMethod
    ) {
        if (!instrumentedMethod.getReturnType().asErasure().represents(long.class)) {
            throw new IllegalArgumentException(instrumentedMethod + " must return long");
        }
        StackManipulation.Size operandStackSize = LongFib.INSTANCE.apply(methodVisitor, implementationContext);
        return new Size(operandStackSize.getMaximalSize(), instrumentedMethod.getStackSize());
    }
}
