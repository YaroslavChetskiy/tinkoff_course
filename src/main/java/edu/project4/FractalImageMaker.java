package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.Rectangle;
import edu.project4.postProcessing.ImageProcessor;
import edu.project4.renderer.Renderer;
import edu.project4.transformation.Transformation;
import edu.project4.util.ImageFormat;
import edu.project4.util.ImageUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FractalImageMaker {

    private final Renderer renderer;
    private final List<Transformation> transformations;
    private final List<ImageProcessor> processors;
    private final Rectangle world;

    private final Path dir;
    private final ImageFormat format;

    public FractalImageMaker(
        Renderer renderer,
        List<Transformation> transformations,
        List<ImageProcessor> processors,
        Rectangle world,
        Path dir,
        ImageFormat format
    ) {
        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Path should be directory: " + dir);
        }
        this.renderer = renderer;
        this.transformations = transformations;
        this.processors = processors;
        this.world = world;
        this.dir = dir;
        this.format = format;
    }

    public void makeImage(
        String fileName,
        int width,
        int height,
        int samples,
        int affineCount,
        short iterations,
        int symmetry
    ) {
        var image = renderer.render(
            FractalImage.create(width, height),
            world,
            transformations,
            samples,
            affineCount,
            iterations,
            symmetry
        );
        for (ImageProcessor processor : processors) {
            processor.process(image);
        }
        ImageUtils.save(
            image,
            dir.resolve(fileName + format.getExtension()),
            format
        );
    }
}
