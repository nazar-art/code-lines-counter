package com.codeminders.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class CountLinesResult {

    private String fileName;
    private int totalLinesCount;

    private List<CountLinesResult> children = new ArrayList<>();

    public CountLinesResult(String fileName) {
        this.fileName = fileName;
    }

    public CountLinesResult(String fileName, int totalLinesCount, List<CountLinesResult> children) {
        this.fileName = fileName;
        this.totalLinesCount = totalLinesCount;
        this.children = children;
    }

    public static CountLinesResult of(String fileName, int total) {
        return new CountLinesResult(fileName, total, null);
    }

    public String getFileName() {
        return fileName;
    }

    public int getTotalLinesCount() {
        return totalLinesCount;
    }

    public List<CountLinesResult> getChildren() {
        return children;
    }
}
