package edu.project4.model;

public record Rectangle(double x, double y, double width, double height) {
    public boolean contains(Point p) {
        return (p.x() >= x) && (p.x() < (x + width)) && (p.y() >= y) && (p.y() < (y + height));
    }
}
