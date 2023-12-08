package edu.project4.util;

import edu.project4.exception.ImageUtilsException;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public final class ImageUtils {

    public static final int RED_SHIFT = 16;
    public static final int GREEN_SHIFT = 8;

    private ImageUtils() {
    }

    public static void save(FractalImage image, Path fileName, ImageFormat format) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), TYPE_INT_RGB);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.getPixel(x, y);
                int rgb = (pixel.getR() << RED_SHIFT) | (pixel.getG() << GREEN_SHIFT) | pixel.getB();
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        try {
            ImageIO.write(bufferedImage, format.name(), fileName.toFile());
        } catch (IOException e) {
            throw new ImageUtilsException("Could not save image in path: " + fileName, e);
        }
    }
}
