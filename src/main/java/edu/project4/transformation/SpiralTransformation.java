package edu.project4.transformation;

import edu.project4.model.Point;

public class SpiralTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        var arctan = Math.atan(point.x() / point.y());
        var sqrt = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        return new Point(
            1 / sqrt * (Math.cos(arctan) + Math.sin(sqrt)),
            1 / sqrt * (Math.sin(arctan) + Math.cos(sqrt))
        );
    }
}
