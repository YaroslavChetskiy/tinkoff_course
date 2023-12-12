package edu.project4.transformation;

import edu.project4.model.Point;

public class SwirlTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        var sm = point.x() * point.x() + point.y() * point.y();
        return new Point(
            point.x() * Math.sin(sm) - point.y() * Math.cos(sm),
            point.x() * Math.cos(sm) + point.y() * Math.sin(sm)
        );
    }
}
