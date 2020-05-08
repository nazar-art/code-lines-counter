package com.codeminders.model;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class LinesStats {
    private Path resource;
    private int linesCount;
    private List<LinesStats> subResources;

    private LinesStats() {
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
        return new LinesStats().new Builder();
    }

    @Override
    public String toString() {
        return "LinesStats{" +
                "resource=" + resource +
                ", linesCount=" + linesCount +
                ", subResources=" + subResources +
                '}';
    }

    public class Builder {

        private Builder() {
        }

        public Builder resource(Path path) {
            LinesStats.this.resource = path;
            return this;
        }

        public Builder linesCount(int total) {
            LinesStats.this.linesCount = total;
            return this;
        }

        public Builder subResources(List<LinesStats> resources) {
            if (resources == null) {
                throw new IllegalArgumentException("sub resources can't be null");
            }
            LinesStats.this.subResources = resources;
            return this;
        }

        public LinesStats build() {
            return LinesStats.this;
        }
    }
}
