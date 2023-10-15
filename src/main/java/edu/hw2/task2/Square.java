package edu.hw2.task2;

public class Square extends Rectangle {

    public Square(int side) {
        super(side, side);
    }

    public static SquareBuilder builder() {
        return new SquareBuilder();
    }

    // Особо не наследовал Builder'ы, поэтому не знаю, насколько это целесообразно
    public static class SquareBuilder extends RectangleBuilder {
        private int side;

        public SquareBuilder() {
        }

        public RectangleBuilder side(int side) {
            this.side = side;
            return this;
        }

        @Override
        public RectangleBuilder height(int height) {
            return side(height);
        }

        @Override
        public RectangleBuilder width(int width) {
            return side(width);
        }

        @Override
        public Rectangle build() {
            return new Square(side);
        }
    }
}
