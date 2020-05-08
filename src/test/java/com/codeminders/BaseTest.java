package com.codeminders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Nazar Lelyak.
 */
public interface BaseTest {
    @BeforeEach
    default void setUp(TestInfo testInfo, TestReporter reporter) {
        String infoMessage = String
                .format("Running: %s with Tags: %s%n", testInfo.getDisplayName(), testInfo.getTags());
        reporter.publishEntry(infoMessage);
    }

    default Path buildPath(String name) {
        return Paths.get(name);
    }
}
