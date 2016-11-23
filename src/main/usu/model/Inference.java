package main.usu.model;

public class Inference extends Position {
    private String exclusion;

    public Inference(int row, int col, String exclusion) {
        super(row, col);
        this.exclusion = exclusion;
    }

    public boolean equals(Object otherObj) {
        Inference other = (Inference) otherObj;
        return (this.row == other.row && this.col == other.col && this.exclusion == other.exclusion);
    }

    public String toString() {
        return super.toString() + "-" + exclusion;
    }

    public String getExclusion() {
        return exclusion;
    }
}