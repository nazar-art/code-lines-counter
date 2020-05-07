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
public class DirectoryCodeLineCounter implements LinesCounter {

    private Path filePath;
    private List<? extends LinesCounter> subResources;

    public DirectoryCodeLineCounter(Path filePath) {
        this.filePath = filePath;

        if (Files.isDirectory(filePath)) {
            collectSubResources(filePath);
        }
    }

    private void collectSubResources(Path resource) {
        try (Stream<Path> entries = Files.list(resource)) {
            subResources = entries
                    .map(DirectoryCodeLineCounter::new)
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
            JavaCodeLinesCounter javaCodeLinesCounter = new JavaCodeLinesCounter(filePath);
            report = LinesStats.builder()
                    .resource(filePath)
                    .linesCount(javaCodeLinesCounter.countLinesForFile())
                    .build();
        }

        return report;
    }
}
