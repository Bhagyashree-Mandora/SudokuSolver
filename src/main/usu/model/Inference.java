package main.usu.model;

public class Inference extends Position {
    private String value;

    public Inference(int row, int col, String value) {
        super(row, col);
        this.value = value;
    }

    public boolean equals(Object otherObj) {
        Inference other = (Inference) otherObj;
        return (this.row == other.row && this.col == other.col && this.value == other.value);
    }

    public String toString() {
        return super.toString() + SudokuPuzzle.NIL + value;
    }

    public String getValue() {
        return value;
    }
}