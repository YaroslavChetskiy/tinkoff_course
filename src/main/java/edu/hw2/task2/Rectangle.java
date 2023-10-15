package edu.hw2.task2;

// подсказка 1 наводит на мысль, что в задании просят сделать Builder
public class Rectangle {

    private static final int DEFAULT_SIDE_SIZE = 1;

    private final int height;
    private final int width;

    public Rectangle() {
        this.height = DEFAULT_SIDE_SIZE;
        this.width = DEFAULT_SIDE_SIZE;
    }

    public Rectangle(int height, int width) {
        if (height <= 0 || width < 0) {
            throw new IllegalArgumentException("Invalid side sizes");
        }
        this.height = height;
        this.width = width;
    }

    // не знаю, почему в условии double возвращается, когда он получится не может, поэтому сделал int
    public int area() {
        return height * width;
    }

    public static RectangleBuilder builder() {
        return new RectangleBuilder();
    }

    public static class RectangleBuilder {
        private int height;
        private int width;

        RectangleBuilder() {
        }

        // можно было бы и setHeight()/setWidth(), но с Lombok'ом привык к просто height()/width()
        public RectangleBuilder height(int height) {
            this.height = height;
            return this;
        }

        public RectangleBuilder width(int width) {
            this.width = width;
            return this;
        }

        public Rectangle build() {
            return new Rectangle(height, width);
        }
    }
}
