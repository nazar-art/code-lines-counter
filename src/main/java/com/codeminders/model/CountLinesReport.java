package com.codeminders.model;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class CountLinesReport {

    private Path resource;
    private int totalLinesCount;
    private List<CountLinesReport> children;

    private CountLinesReport(Path resource, int totalLinesCount, List<CountLinesReport> children) {
        this.resource = resource;
        this.totalLinesCount = totalLinesCount;
        this.children = children;
    }

    public Path getResource() {
        return resource;
    }

    public int getTotalLinesCount() {
        return totalLinesCount;
    }

    public List<CountLinesReport> getChildren() {
        return children;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Path path;
        private int totalLinesCount;
        private List<CountLinesReport> children;

        public Builder filePath(Path fileName) {
            this.path = fileName;
            return this;
        }

        public Builder totalLinesCount(int totalLinesCount) {
            this.totalLinesCount = totalLinesCount;
            return this;
        }

        public Builder children(List<CountLinesReport> children) {
            this.children = children;
            return this;
        }

        public CountLinesReport build() {
            return new CountLinesReport(path, totalLinesCount, children);
        }
    }
}
