package com.codeminders.counter;

import com.codeminders.model.LinesStats;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Nazar Lelyak.
 */
@FunctionalInterface
public interface LinesCounter {

    LinesStats countLines();

    default void validateResource(Path path) {
        if (path == null || !Files.exists(path)) {
            throw new IllegalArgumentException("Resource is null or doesn't exist: " + path);
        }
    }
}
