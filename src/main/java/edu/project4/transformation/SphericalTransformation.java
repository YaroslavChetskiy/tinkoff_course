package edu.project4.transformation;

import edu.project4.model.Point;

public class SphericalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        var sm = point.x() * point.x() + point.y() * point.y();
        if (sm == 0) {
            return point;
        }
        return new Point(point.x() / sm, point.y() / sm);
    }
}
