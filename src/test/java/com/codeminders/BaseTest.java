package com.codeminders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Nazar Lelyak.
 */
@EnabledOnOs({OS.LINUX, OS.MAC})
public interface BaseTest {
    @BeforeEach
    default void setUp(TestInfo testInfo, TestReporter reporter) {
        reporter.publishEntry(String.format(
                "Running: %s with Tags: %s%n",
                testInfo.getDisplayName(),
                testInfo.getTags()
        ));
    }

    default Path buildPath(String name) {
        return Paths.get(name);
    }
}
