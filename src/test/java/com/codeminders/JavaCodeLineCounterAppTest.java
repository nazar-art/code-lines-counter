package com.codeminders;

import com.codeminders.counter.DirectoryLineCounter;
import com.codeminders.counter.FileLinesCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for Java Code Lines Counting App.
 *
 * @author Nazar Lelyak.
 */
@EnabledOnOs({OS.LINUX, OS.MAC})
@DisplayName("Testing Java Code Lines Counting App class")
public class JavaCodeLineCounterAppTest {

    /**
     * Pre configuration
     */
    @BeforeEach
    void setUp(TestInfo testInfo, TestReporter reporter) {
        String infoMessage = String
                .format("Running: %s with Tags: %s%n", testInfo.getDisplayName(), testInfo.getTags());

        reporter.publishEntry(infoMessage);
    }

    @Nested
    @Tag("File")
    @DisplayName("Testing file's resources")
    class FileResourceTest {

        @Test
        void testPositiveCases() {
            assertAll(
                    () -> assertEquals(3,
                            new FileLinesCounter(buildPath("src/test/resources/valid/3_code_lines.java")).countLines().getLinesCount(),
                            "line count should return 3 lines of code"),

                    () -> assertEquals(5,
                            new FileLinesCounter(buildPath("src/test/resources/valid/5_code_lines.java")).countLines().getLinesCount(),
                            "line count should return 5 lines of code"),

                    () -> assertEquals(0,
                            new FileLinesCounter(buildPath("src/test/resources/valid/0_code_lines.java")).countLines().getLinesCount(),
                            "line count should return 0 lines of code"),

                    () -> assertEquals(0,
                            new FileLinesCounter(buildPath("src/test/resources/empty/empty.java")).countLines().getLinesCount(),
                            "line count should return 0 lines of code")
            );
        }

        @Test
        void testNegativeCases() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new FileLinesCounter(buildPath("src/this_file_doesnt_exist.java")).countLines(),
                            "if file doesn't throw exception"),

                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new FileLinesCounter(buildPath("src/test/resources/invalid/mat-photo.jpg")).countLines(),
                            "if file is photo throw exception"),

                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new FileLinesCounter(buildPath("src/test/resources/invalid/test.txt")).countLines(),
                            "if file is incorrect throw exception"),

                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new FileLinesCounter(null).countLines(),
                            "if resource is null exception should be thrown"),

                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new FileLinesCounter(buildPath("")).countLines(),
                            "if resource name is empty exception should be thrown"),

                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new FileLinesCounter(buildPath("src/test/resources/invalid/test.json")).countLines(),
                            "if file is json throw exception")
            );
        }
    }

    private Path buildPath(String name) {
        return Paths.get(name);
    }

    @Nested
    @Tag("Folder")
    @DisplayName("Testing folder's resource")
    class FolderResourceTest {

        @Test
        void testPositiveCases() {
            assertAll(
                    () -> assertEquals(8,
                            new DirectoryLineCounter(buildPath("src/test/resources/valid")).countLines().getLinesCount(),
                            "valid folder contains 3 files with 8 in sum java code lines - 3 + 0 + 5"),

                    () -> assertEquals(0,
                            new DirectoryLineCounter(buildPath("src/test/resources/empty")).countLines().getLinesCount(),
                            "empty folder contains 0 lines")
            );
        }

        @Test
        void testNegativeCases() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new DirectoryLineCounter(buildPath("src/folder_not_exists")).countLines().getLinesCount(),
                            "if folder is invalid exception should be thrown"),

                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new DirectoryLineCounter(buildPath("src/test/resources/invalid")).countLines().getLinesCount(),
                            "if folder content is invalid exception should be thrown")
            );
        }
    }

}

