package edu.project4.model;

public record FractalImage(Pixel[] data, int width, int height) {

    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[width * y + x] = new Pixel(0, 0, 0, 0);
            }
        }
        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel getPixel(int x, int y) {
        if (contains(x, y)) {
            return data[width * y + x];
        } else {
            return null;
        }
    }
}
