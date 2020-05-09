package com.codeminders.counter;

import com.codeminders.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for Java Code Lines Counting App.
 * 
 * @author Nazar Lelyak.
 */
@EnabledOnOs({OS.LINUX, OS.MAC})
@DisplayName("Testing Java Code Lines Counting App class")
public class CodeLineCounterTest implements BaseTest {

    @Nested
    @Tag("File")
    @DisplayName("Testing file's resources")
    class FileResourceTest {

        @Test
        void testPositiveCases() {
            assertEquals(3,
                    getFileCount("src/test/resources/valid/3_code_lines.java"),
                    "line count should return 3 lines of code");

            assertEquals(5,
                    getFileCount("src/test/resources/valid/5_code_lines.java"),
                    "line count should return 5 lines of code");

            assertEquals(0,
                    getFileCount("src/test/resources/valid/0_code_lines.java"),
                    "line count should return 0 lines of code");

            assertEquals(0,
                    getFileCount("src/test/resources/empty/empty.java"),
                    "empty file should return 0 lines");

        }

        private int getFileCount(String file) {
            return new FileLinesCounter(buildPath(file)).countLines().getLinesCount();
        }

        @Test
        void testNegativeCases() {
            assertThrows(IllegalArgumentException.class,
                    () -> getFileCount("src/test/resources/invalid/mat-photo.jpg"),
                    "if file is photo throw exception");

            assertThrows(IllegalArgumentException.class,
                    () -> getFileCount("src/test/resources/invalid/test.txt"),
                    "if file is incorrect throw exception");

            assertThrows(IllegalArgumentException.class,
                    () -> getFileCount(""),
                    "if resource name is empty exception should be thrown");

            assertThrows(IllegalArgumentException.class,
                    () -> getFileCount("src/test/resources/invalid/test.json"),
                    "if file is json throw exception");
        }
    }

    @Nested
    @Tag("Folder")
    @DisplayName("Testing folder's resource")
    class FolderResourceTest {

        @Test
        void testPositiveCases() {
            assertEquals(8,
                    getFolderCount("src/test/resources/valid"),
                    "valid folder contains 3 files with 8 in sum java code lines - 3 + 0 + 5");

            assertEquals(0,
                    getFolderCount("src/test/resources/empty"),
                    "empty folder contains 0 lines");
        }

        private int getFolderCount(String folder) {
            return new FolderLinesCounter(buildPath(folder)).countLines().getLinesCount();
        }

        @Test
        void testNegativeCases() {
            assertThrows(IllegalArgumentException.class,
                    () -> getFolderCount("src/folder_not_exists"),
                    "if folder doesn't exist exception should be thrown");

            assertThrows(IllegalArgumentException.class,
                    () -> getFolderCount("src/test/resources/invalid"),
                    "if folder content is invalid exception should be thrown");
        }
    }
}

