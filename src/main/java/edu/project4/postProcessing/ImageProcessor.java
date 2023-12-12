package edu.project4.postProcessing;

import edu.project4.model.FractalImage;

@FunctionalInterface
public interface ImageProcessor {

    void process(FractalImage image);
}
