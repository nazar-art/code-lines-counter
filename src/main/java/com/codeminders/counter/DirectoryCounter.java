package com.codeminders.counter;

import com.codeminders.model.CountLinesReport;

import java.nio.file.Path;

/**
 * @author Nazar Lelyak.
 */
public class DirectoryCounter implements LinesCounter {

    private Path root;

    public DirectoryCounter(Path path) {
        root = path;
    }

    @Override
    public CountLinesReport countLines() {
        return null;
    }
}
