package edu.project4.postProcessing;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;

public class GammaCorrectionProcessor implements ImageProcessor {

    private static final double GAMMA = 2.2;

    @Override
    public void process(FractalImage image) {
        var max = getMaxNormal(image);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.getPixel(x, y);
                var normal = Math.log10(pixel.getHitCount()) / max;

                pixel.setR((int) Math.round(pixel.getR() * Math.pow(normal, (1.0 / GAMMA))));
                pixel.setG((int) Math.round(pixel.getG() * Math.pow(normal, (1.0 / GAMMA))));
                pixel.setB((int) Math.round(pixel.getB() * Math.pow(normal, (1.0 / GAMMA))));

            }
        }
    }

    private double getMaxNormal(FractalImage image) {
        double max = 0.0;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                var pixel = image.getPixel(x, y);
                max = Math.max(Math.log10(pixel.getHitCount()), max);
            }
        }
        return max;
    }
}
