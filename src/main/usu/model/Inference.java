package main.usu.model;

public class Inference extends Index {
    private int exclusion;

    public Inference(int row, int col, int exclusion) {
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

    public int getExclusion() {
        return exclusion;
    }
}