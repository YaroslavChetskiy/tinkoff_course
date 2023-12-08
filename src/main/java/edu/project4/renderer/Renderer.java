package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.Rectangle;
import edu.project4.transformation.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {

    FractalImage render(
        FractalImage canvas,
        Rectangle world,
        List<Transformation> variations,
        int samples,
        int affineCount,
        short iterPerSample,
        int symmetry
    );
}
