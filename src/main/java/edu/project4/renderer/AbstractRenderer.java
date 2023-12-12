package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rectangle;
import edu.project4.transformation.AffineTransformation;

public abstract class AbstractRenderer implements Renderer {

    public static final int START_STEP = -20;

    protected Point rotate(Point pw, double theta) {
        double x = pw.x() * Math.cos(theta) - pw.y() * Math.sin(theta);
        double y = pw.x() * Math.sin(theta) + pw.y() * Math.cos(theta);
        return new Point(x, y);
    }

    protected Point mapRange(Rectangle world, Point pwr, FractalImage canvas) {
        double xPercent = (pwr.x() - world.x()) / world.width();
        double yPercent = (pwr.y() - world.y()) / world.height();

        int canvasX = (int) (xPercent * canvas.width());
        int canvasY = (int) (yPercent * canvas.height());

        return new Point(canvasX, canvasY);
    }

    protected void changePixel(Pixel pixel, AffineTransformation affine) {
        if (pixel.getHitCount() == 0) {
            pixel.setR(affine.getRed());
            pixel.setG(affine.getGreen());
            pixel.setB(affine.getBlue());
            pixel.setHitCount(1);
        } else {
            pixel.setR((pixel.getR() + affine.getRed()) / 2);
            pixel.setG((pixel.getG() + affine.getGreen()) / 2);
            pixel.setB((pixel.getB() + affine.getBlue()) / 2);
            pixel.setHitCount(pixel.getHitCount() + 1);
        }
    }

}
