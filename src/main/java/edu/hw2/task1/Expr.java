package edu.hw2.task1;

public sealed interface Expr {

    double evaluate();

    public record Constant(double value) implements Expr {

        @Override
        public double evaluate() {
            return value;
        }
    }

    public record Negate(Expr operand) implements Expr {

        @Override
        public double evaluate() {
            return -(operand.evaluate());
        }
    }

    public record Exponent(Expr base, double exponent) implements Expr {

        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), exponent);
        }
    }

    public record Addition(Expr firstValue, Expr secondValue) implements Expr {

        @Override
        public double evaluate() {
            return firstValue().evaluate() + secondValue().evaluate();
        }
    }

    public record Multiplication(Expr firstValue, Expr secondValue) implements Expr {

        @Override
        public double evaluate() {
            return firstValue.evaluate() * secondValue().evaluate();
        }
    }
}
