package edu.project4.transformation;

import edu.project4.model.Point;

public class EyefishTransformation implements Transformation {

    // 2 / (r + 1) * (x, y)
    // r = sqrt(x*x+y*y)

    @Override
    public Point apply(Point point) {
        var sqrt = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        return new Point(2 / (sqrt + 1) * point.x(), 2 / (sqrt + 1) * point.y());
    }
}
