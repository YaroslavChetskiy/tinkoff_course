package edu.hw9.task2.handler;

import edu.hw9.task2.exception.FileSystemHandlerException;
import edu.hw9.task2.task.DirectoriesSearchTask;
import edu.hw9.task2.task.FilesSearchTask;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FileSystemHandler {

    private final int numberOfThreads;

    public FileSystemHandler(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public List<Path> getDirectoriesWithMoreThanNthFiles(Path dir, int filesCount) {
        return getResultPathList(dir, new DirectoriesSearchTask(dir, filesCount));
    }

    public List<Path> getFilesWithSizeAndExtension(Path dir, long size, String extension) {
        return getResultPathList(dir, new FilesSearchTask(dir, size, extension));
    }

    private List<Path> getResultPathList(Path dir, RecursiveTask<List<Path>> task) {
        if (!Files.isDirectory(dir)) {
            throw new FileSystemHandlerException("Path is not directory", new IllegalArgumentException());
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfThreads);
        var result = forkJoinPool.invoke(task);
        forkJoinPool.shutdown();
        return result;
    }
}
