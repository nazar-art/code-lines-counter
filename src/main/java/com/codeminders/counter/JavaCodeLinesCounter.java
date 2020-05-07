package com.codeminders.counter;

import com.codeminders.model.LinesStats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nazar Lelyak.
 */
public class JavaCodeLinesCounter implements LinesCounter {

    private static final String BLOCK_CODE_START = "/*";
    private static final String BLOCK_CODE_END = "*/";
    private static final String LINE_CODE = "//";

    private static final String INLINE_BLOCK_COMMENT_REGEX = "/\\*/*(?s:(?!\\*/).)*\\*/";

    private final Path filePath;
    private List<JavaCodeLinesCounter> subResources;

    public JavaCodeLinesCounter(Path resource) {
        this.filePath = resource;

        if (Files.isDirectory(resource)) {
            collectSubResources(resource);
        }
    }

    private void collectSubResources(Path resource) {
        try (Stream<Path> entries = Files.list(resource)) {
            subResources = entries
                    .map(JavaCodeLinesCounter::new)
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
                    .map(JavaCodeLinesCounter::countLines)
                    .collect(Collectors.toList());

            report = LinesStats.builder()
                    .root(filePath)
                    .resources(subResourcesResults)
                    .build();
        } else {
            report = LinesStats.builder()
                    .root(filePath)
                    .linesCount(countLinesForFile())
                    .build();
        }
        return report;
    }

    private int countLinesForFile() {
        int counter = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath.toFile())))) {
            boolean isBlockComment = false;

            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith(BLOCK_CODE_START)) {
                    isBlockComment = true;
                    continue;
                }

                // process block comments
                if (line.contains(BLOCK_CODE_START) && line.contains(BLOCK_CODE_END)) {
                    line = line.replaceAll(INLINE_BLOCK_COMMENT_REGEX, "");
                    isBlockComment = false;

                } else if (line.contains(BLOCK_CODE_END) && isBlockComment) {
                    line = line.replace(BLOCK_CODE_END, "");
                    isBlockComment = false;

                } else if (isBlockComment) {
                    continue;
                }

                // line comments
                if (line.contains(LINE_CODE)) {
                    line = processLineComment(line);
                }

                // count results
                if (!line.isEmpty()) {
                    counter += 1;
//                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Exception during processing file: " + filePath);
        }
        return counter;
    }

    private String processLineComment(String line) {
        return line.substring(0, line.indexOf(LINE_CODE));
    }

}
