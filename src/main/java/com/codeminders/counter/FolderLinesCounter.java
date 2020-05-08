package com.codeminders.counter;

import com.codeminders.model.LinesStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nazar Lelyak.
 */
public class FolderLinesCounter implements LinesCounter {

    private Path root;
    private List<? extends LinesCounter> subFolders;

    public FolderLinesCounter(Path path) {
        validateResource(path);
        root = path;

        if (Files.isDirectory(root)) {
            collectSubResources(path);
        }
    }

    private void collectSubResources(Path resource) {
        try (Stream<Path> entries = Files.list(resource)) {
            subFolders = entries
                    .map(FolderLinesCounter::new)
                    .sorted(Comparator.comparing(d -> d.root))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Exception while traversing sub resources: " + e.getMessage());
        }
    }

    @Override
    public LinesStats countLines() {
        LinesStats stats;
        if (Files.isDirectory(root)) {

            List<LinesStats> subResourcesResults = subFolders.stream()
                    .map(LinesCounter::countLines)
                    .collect(Collectors.toList());

            int total = subResourcesResults.stream()
                    .map(LinesStats::getLinesCount)
                    .reduce(0, Integer::sum);

            stats = LinesStats.builder()
                    .resource(root)
                    .linesCount(total)
                    .subResources(subResourcesResults)
                    .build();
        } else {
            FileLinesCounter fileLinesCounter = new FileLinesCounter(root);
            stats = LinesStats.builder()
                    .resource(root)
                    .linesCount(fileLinesCounter.countLinesForFile())
                    .build();
        }

        return stats;
    }
}
