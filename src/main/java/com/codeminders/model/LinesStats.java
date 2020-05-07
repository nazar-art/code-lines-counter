package com.codeminders.model;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class LinesStats {

    private Path root;
    private int linesCount;
    private List<LinesStats> resources;

    private LinesStats(Path root, int linesCount, List<LinesStats> resources) {
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

    public List<LinesStats> getResources() {
        return resources;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int calculateCodeLines() {
        return (resources == null || resources.isEmpty())
                ? linesCount
                : resources.stream()
                    .mapToInt(LinesStats::getLinesCount)
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
        private List<LinesStats> resources;

        public Builder root(Path path) {
            this.root = path;
            return this;
        }

        public Builder linesCount(int total) {
            this.linesCount = total;
            return this;
        }

        public Builder resources(List<LinesStats> children) {
            this.resources = children;
            return this;
        }

        public LinesStats build() {
            return new LinesStats(root, linesCount, resources);
        }
    }
}
