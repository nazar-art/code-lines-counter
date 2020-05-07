package com.codeminders.model;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class CountLinesStats {

    private Path root;
    private int linesCount;
    private List<CountLinesStats> resources;

    private CountLinesStats(Path root, int linesCount, List<CountLinesStats> resources) {
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

    public List<CountLinesStats> getResources() {
        return resources;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int calculateCodeLines() {
        return (resources == null || resources.isEmpty())
                ? linesCount
                : resources.stream()
                    .mapToInt(CountLinesStats::getLinesCount)
                    .sum();

    }

    @Override
    public String toString() {
        return "CountLinesReport{" +
                "root=" + root +
                ", linesCount=" + linesCount +
                ", resources=" + resources +
                '}';
    }

    public static class Builder {
        private Path root;
        private int linesCount;
        private List<CountLinesStats> resources;

        public Builder root(Path path) {
            this.root = path;
            return this;
        }

        public Builder linesCount(int total) {
            this.linesCount = total;
            return this;
        }

        public Builder resources(List<CountLinesStats> children) {
            this.resources = children;
            return this;
        }

        public CountLinesStats build() {
            return new CountLinesStats(root, linesCount, resources);
        }
    }
}
