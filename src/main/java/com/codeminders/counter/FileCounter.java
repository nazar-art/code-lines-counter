package com.codeminders.counter;

import com.codeminders.model.CountLinesReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nazar Lelyak.
 */
public class FileCounter implements LinesCounter {

    private static final String BLOCK_CODE_START = "/*";
    private static final String BLOCK_CODE_END = "*/";
    private static final String LINE_CODE = "//";

    private static final String INLINE_BLOCK_COMMENT_REGEX = "/\\*/*(?s:(?!\\*/).)*\\*/";

    private final Path filePath;
    private CountLinesReport report;

    private List<FileCounter> subResources;

    public FileCounter(Path resource) {
        this.filePath = resource;

        if (Files.isDirectory(resource)) {
            collectSubResources(resource);

        } else if (!Files.exists(filePath)
                || !resource.getFileName().toString().toLowerCase().endsWith(".java")) {
            throw new IllegalArgumentException("Incorrect resource: " + resource);
        }
    }

    private void collectSubResources(Path resource) {
        try (Stream<Path> entries = Files.list(resource)) {
            subResources = entries
                    .map(FileCounter::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Exception while traversing sub resources: " + e.getMessage());
        }
    }

    @Override
    public CountLinesReport countLines() {

        if (Files.isDirectory(filePath)) {
            // todo consider to use Tasks and execution service here
            List<CountLinesReport> subResourcesResults = subResources.stream()
                    .map(FileCounter::countLines)
                    .collect(Collectors.toList());

            return CountLinesReport.builder()
                    .root(filePath)
                    .resources(subResourcesResults)
                    .build();
        } else {
            return countLinesForFile();
        }
    }

    private CountLinesReport countLinesForFile() {
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
        return report = CountLinesReport.builder()
                .root(filePath)
                .linesCount(counter)
                .build();
    }

    private String processLineComment(String line) {
        return line.substring(0, line.indexOf(LINE_CODE));
    }

    public static void main(String[] args) {
        String [] arr = {
                "src/test/resources/3_code_lines.java",
                "src/test/resources/5_code_lines.java",
                "src/test/resources"
        };

        for (String a : arr) {
            FileCounter fileCounter = new FileCounter(Paths.get(a));
            CountLinesReport report = fileCounter.countLines();
            System.out.println(report.toString());
        }
    }
}
