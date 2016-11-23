package main.usu.algorithms;

import main.usu.model.Position;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class DFS extends SudokuAlgorithm {
    @Override
    public LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Position unassigned, String value) {
        return new LinkedList<>();
    }
}
