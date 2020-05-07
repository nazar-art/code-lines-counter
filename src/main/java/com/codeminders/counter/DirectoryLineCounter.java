package com.codeminders.counter;

import com.codeminders.model.LinesStats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nazar Lelyak.
 */
public class DirectoryLineCounter implements LinesCounter {

    private Path filePath;
    private List<? extends LinesCounter> subResources;

    public DirectoryLineCounter(Path filePath) {
        validateResource(filePath);
        this.filePath = filePath;

        if (Files.isDirectory(this.filePath)) {
            collectSubResources(filePath);
        }
    }

    private void collectSubResources(Path resource) {
        try (Stream<Path> entries = Files.list(resource)) {
            subResources = entries
                    .map(DirectoryLineCounter::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Exception while traversing sub resources: " + e.getMessage());
        }
    }

    @Override
    public LinesStats countLines() {
        LinesStats report;
        if (Files.isDirectory(filePath)) {

            List<LinesStats> subResourcesResults = subResources.stream()
                    .map(LinesCounter::countLines)
                    .collect(Collectors.toList());

            report = LinesStats.builder()
                    .resource(filePath)
                    .subResources(subResourcesResults)
                    .build();
        } else {
            FileLinesCounter fileLinesCounter = new FileLinesCounter(filePath);
            report = LinesStats.builder()
                    .resource(filePath)
                    .linesCount(fileLinesCounter.countLinesForFile())
                    .build();
        }

        return report;
    }
}
