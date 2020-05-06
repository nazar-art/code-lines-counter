package com.codeminders.model;

import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class CountLinesReport {

    private String fileName;
    private int totalLinesCount;
    private List<CountLinesReport> children;

    private CountLinesReport(String fileName, int totalLinesCount, List<CountLinesReport> children) {
        this.fileName = fileName;
        this.totalLinesCount = totalLinesCount;
        this.children = children;
    }

    public String getFileName() {
        return fileName;
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
        private String fileName;
        private int totalLinesCount;
        private List<CountLinesReport> children;

        public Builder fileName(String fileName) {
            this.fileName = fileName;
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
            return new CountLinesReport(fileName, totalLinesCount, children);
        }
    }
}
