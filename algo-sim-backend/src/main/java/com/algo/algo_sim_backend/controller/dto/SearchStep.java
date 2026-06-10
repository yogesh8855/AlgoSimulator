package com.algo.algo_sim_backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchStep {
    private String type;
    private int row;
    private int col;
    private Integer g;
    private Integer h;
    private Integer f;
    private String note;

    private Integer nodeId;
    private int[] parent;
    private List<int[]> pqSnapshot;

    public SearchStep() {}

    public SearchStep(String type, int row, int col) {
        this.type = type; this.row = row; this.col = col;
    }

    public SearchStep(String type, int row, int col, Integer g, Integer h, Integer f, String note) {
        this.type = type; this.row = row; this.col = col; this.g = g; this.h = h; this.f = f; this.note = note;
    }

    // getters / setters (Jackson needs them)
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }

    public Integer getG() { return g; }
    public void setG(Integer g) { this.g = g; }

    public Integer getH() { return h; }
    public void setH(Integer h) { this.h = h; }

    public Integer getF() { return f; }
    public void setF(Integer f) { this.f = f; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Integer getNodeId() { return nodeId; }
    public void setNodeId(Integer nodeId) { this.nodeId = nodeId; }

    public int[] getParent() { return parent; }
    public void setParent(int[] parent) { this.parent = parent; }

    public List<int[]> getPqSnapshot() { return pqSnapshot; }
    public void setPqSnapshot(List<int[]> pqSnapshot) { this.pqSnapshot = pqSnapshot; }

    @Override
    public String toString() {
        return "SearchStep{" + "type='" + type + '\'' + ", row=" + row + ", col=" + col + ", g=" + g + ", h=" + h + ", f=" + f + ", note='" + note + '\'' + '}';
    }
}
