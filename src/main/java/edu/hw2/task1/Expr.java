package edu.hw2.task1;

public sealed interface Expr {

    double evaluate();

    public record Constant(double value) implements Expr {

        @Override
        public double evaluate() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public record Negate(Expr operand) implements Expr {

        @Override
        public double evaluate() {
            return -(operand.evaluate());
        }

        @Override
        public String toString() {
            return "-" + operand.toString();
        }
    }

    public record Exponent(Expr base, double exponent) implements Expr {

        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), exponent);
        }

        @Override
        public String toString() {
            return "(" + base.toString() + ")" + "^" + exponent;
        }
    }

    public record Addition(Expr firstValue, Expr secondValue) implements Expr {

        @Override
        public double evaluate() {
            return firstValue().evaluate() + secondValue().evaluate();
        }

        @Override
        public String toString() {
            return firstValue.toString() + " + " + secondValue.toString();
        }
    }

    public record Multiplication(Expr firstValue, Expr secondValue) implements Expr {

        @Override
        public double evaluate() {
            return firstValue.evaluate() * secondValue().evaluate();
        }

        @Override
        public String toString() {
            return firstValue.toString() + " * " + secondValue.toString();
        }
    }
}
