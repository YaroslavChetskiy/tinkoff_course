package edu.project4.transformation;

import edu.project4.model.Point;

public class HeartTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        var sqrt = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        var arctan = Math.atan(point.y() / point.x());
        return new Point(sqrt * Math.sin(sqrt * arctan), -sqrt * Math.cos(sqrt * arctan));
    }
}
