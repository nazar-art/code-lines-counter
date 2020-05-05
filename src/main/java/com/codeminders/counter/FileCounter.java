package com.codeminders.counter;

import com.codeminders.model.CountLinesResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Nazar Lelyak.
 */
public class FileCounter implements LinesCounter {

    private final String filePath;
    private final CountLinesResult countLinesResult;

    private enum Comment { // todo consider another format for checking commented code
        BLOCK_BEGGING("/*"),
        BLOCK_ENDING("*/"),
        LINE_COMMENT("//")
        ;

        private String symbol;

        Comment(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        private boolean validate(String line) {
            return line.startsWith(this.getSymbol());
        }
    }

    public FileCounter(String path) {
        this.filePath = path;
        this.countLinesResult = new CountLinesResult(path);
    }

    @Override
    public CountLinesResult countLines() {
        // todo consider how to count all that comments here
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            while (reader.readLine() != null) {

            }

        } catch (IOException e) {
            System.err.println("Exception during processing file: " + filePath);
        }
        return this.countLinesResult;
    }
}
