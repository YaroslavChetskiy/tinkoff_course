package edu.project4.renderer;

import edu.project4.exception.MultiThreadRenderException;
import edu.project4.generator.AffineTransformationGenerator;
import edu.project4.generator.RandomNumberGenerator;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rectangle;
import edu.project4.transformation.AffineTransformation;
import edu.project4.transformation.Transformation;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

// Сначала делал однопоточный вариант, потом многопоточный
// Однопоточный я убрал из-за огромного количества дублирования кода
// (так как его можно добиться, передав в конструктор единицу)
public class MultiThreadRenderer extends AbstractRenderer {

    private final int numberOfThreads;
    private final RandomNumberGenerator numberGenerator;

    public MultiThreadRenderer() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public MultiThreadRenderer(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.numberGenerator = new RandomNumberGenerator(ThreadLocalRandom.current());
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rectangle world,
        List<Transformation> variations,
        int samples,
        int affineCount,
        short iterPerSample,
        int symmetry
    ) {
        var generator = new AffineTransformationGenerator(numberGenerator);

        var affineTransformations = generator.generateAffineTransformations(affineCount);

        Callable<Void> callable = () -> {
            processSample(
                affineTransformations,
                canvas,
                world,
                iterPerSample,
                affineCount,
                variations,
                symmetry
            );
            return null;
        };

        var tasks = Stream.generate(() -> callable).limit(samples).toList();

        try (var executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            var futures = executorService.invokeAll(tasks);

            for (Future<Void> future : futures) {
                future.get();
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new MultiThreadRenderException("Could not process samples", e);
        }

        return canvas;
    }

    private void processSample(
        List<AffineTransformation> affineTransformations,
        FractalImage canvas,
        Rectangle world,
        short iterPerSample,
        int affineCount,
        List<Transformation> variations,
        int symmetry
    ) {
        Point pw = new Point(
            numberGenerator.getRandomDouble(0, world.width()),
            numberGenerator.getRandomDouble(0, world.height())
        );

        for (short step = START_STEP; step < iterPerSample; ++step) {
            int affineIndex = numberGenerator.getRandomInt(0, affineCount);
            var affine = affineTransformations.get(affineIndex);

            pw = affine.apply(pw);

            int variationIndex = numberGenerator.getRandomInt(0, variations.size());
            Transformation variation = variations.get(variationIndex);
            pw = variation.apply(pw);

            double theta2 = 0.0;
            for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, ++s) {
                var pwr = rotate(pw, theta2);

                if (!world.contains(pwr)) {
                    continue;
                }

                Point canvasPoint = mapRange(world, pwr, canvas);
                var pixelX = (int) canvasPoint.x();
                var pixelY = (int) canvasPoint.y();
                Pixel pixel = canvas.getPixel(pixelX, pixelY);
                if (pixel == null) {
                    continue;
                }

                synchronized (pixel) {
                    changePixel(pixel, affine);
                }
            }
        }
    }
}
