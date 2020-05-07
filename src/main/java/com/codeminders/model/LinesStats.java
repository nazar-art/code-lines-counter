package com.codeminders.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * @author Nazar Lelyak.
 */
public class LinesStats {

    private Path resource;
    private int linesCount;
    private List<LinesStats> subResources;

    private LinesStats(Path resource, int linesCount, List<LinesStats> subResources) {
        this.resource = resource;
        this.linesCount = linesCount;
        this.subResources = subResources;
    }

    public Path getResource() {
        return resource;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public List<LinesStats> getSubResources() {
        return subResources;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "LinesStats{" +
                "resource=" + resource +
                ", linesCount=" + linesCount +
                ", subResources=" + subResources +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinesStats)) return false;
        LinesStats that = (LinesStats) o;
        return linesCount == that.linesCount &&
                resource.equals(that.resource) &&
                Objects.equals(subResources, that.subResources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, linesCount, subResources);
    }

    public static class Builder {
        private Path resource;
        private int linesCount;
        private List<LinesStats> subResources;

        public Builder resource(Path path) {
            this.resource = path;
            return this;
        }

        public Builder linesCount(int total) {
            this.linesCount = total;
            return this;
        }

        public Builder subResources(List<LinesStats> resources) {
            this.subResources = resources;
            return this;
        }

        public LinesStats build() {
            return new LinesStats(resource, linesCount, subResources);
        }
    }
}
