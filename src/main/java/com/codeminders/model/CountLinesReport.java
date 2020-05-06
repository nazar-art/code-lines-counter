package com.codeminders.model;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class CountLinesReport {

    private Path root;
    private int linesCount;
    private List<CountLinesReport> resources;

    private CountLinesReport(Path root, int linesCount, List<CountLinesReport> resources) {
        this.root = root;
        this.linesCount = linesCount;
        this.resources = resources;
    }

    public Path getRoot() {
        return root;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public List<CountLinesReport> getResources() {
        return resources;
    }

    @Override
    public String toString() {
        return "CountLinesReport{" +
                "root=" + root +
                ", linesCount=" + linesCount +
                ", resources=" + resources +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public int calculateCodeLines() {
        if (resources == null || resources.isEmpty())
            return linesCount;
        else
            return resources.stream()
                    .mapToInt(CountLinesReport::getLinesCount)
                    .sum();
    }

    public static class Builder {
        private Path root;
        private int linesCount;
        private List<CountLinesReport> resources;

        public Builder root(Path path) {
            this.root = path;
            return this;
        }

        public Builder linesCount(int total) {
            this.linesCount = total;
            return this;
        }

        public Builder resources(List<CountLinesReport> children) {
            this.resources = children;
            return this;
        }

        public CountLinesReport build() {
            return new CountLinesReport(root, linesCount, resources);
        }
    }
}
