package main.usu.model;

public class Index {
    int row;
    int col;

    public Index(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
        return "[" + row + "," + col + "]";
    }
}