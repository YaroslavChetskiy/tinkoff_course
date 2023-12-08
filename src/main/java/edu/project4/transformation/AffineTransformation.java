package edu.project4.transformation;

import edu.project4.model.Point;

public class AffineTransformation implements Transformation {

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    private final int red;
    private final int green;
    private final int blue;

    @SuppressWarnings("ParameterNumber")
    public AffineTransformation(
        double a,
        double b,
        double c,
        double d,
        double e,
        double f,
        int red,
        int green,
        int blue
    ) {
        this.a = a;
        this.b = b;
        this.c = c;

        this.d = d;
        this.e = e;
        this.f = f;

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    // (x_n+1) = (a b) * (x_n) + (c)
    // (y_n+1) = (d e) * (y_n) + (f)
    // Коэффициенты удовлетворяют условиям:
    // 1. a*a + d*d < 1
    // 2. b*b + e*e < 1
    // 3. a*a + b*b + d*d + e*e < 1 + (a*e - b*d)^2

    @Override
    public Point apply(Point point) {
        return new Point(
            a * point.x() + b * point.y() + c,
            d * point.x() + e * point.y() + f
        );
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
