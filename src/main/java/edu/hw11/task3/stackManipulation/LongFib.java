package edu.hw11.task3.stackManipulation;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import static net.bytebuddy.jar.asm.Opcodes.F_SAME;

public enum LongFib implements StackManipulation {

    INSTANCE;

    private static final int MAXIMAL_SIZE = 5;
    public static final String METHOD_NAME = "fib";
    public static final String DESCRIPTOR = "(I)J";

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public @NotNull Size apply(MethodVisitor methodVisitor, Implementation.Context context) {

        Label zeroLabel = new Label();
        Label oneLabel = new Label();
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, zeroLabel);

        // n > 0, сначала проверяем n == 1
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ISUB);
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, oneLabel);

        // n > 1
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitInsn(Opcodes.ISUB); // n - 1

        // Вызываем рекурсивно метод fib(n-1)
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            context.getInstrumentedType().getInternalName(),
            METHOD_NAME,
            DESCRIPTOR,
            false
        );

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitInsn(Opcodes.ISUB); // n - 2

        // Вызываем рекурсивно метод fib(n-2)
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            context.getInstrumentedType().getInternalName(),
            METHOD_NAME,
            DESCRIPTOR,
            false
        );

        // fib(n-1) + fib(n-2)
        methodVisitor.visitInsn(Opcodes.LADD);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        // n == 0
        methodVisitor.visitLabel(zeroLabel);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitInsn(Opcodes.LCONST_0);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        // n == 1
        methodVisitor.visitLabel(oneLabel);
        methodVisitor.visitFrame(F_SAME, 0, null, 0, null);
        methodVisitor.visitInsn(Opcodes.LCONST_1);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        return new Size(0, MAXIMAL_SIZE);
    }
}

