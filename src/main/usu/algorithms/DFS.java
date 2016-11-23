package main.usu.algorithms;

import main.usu.model.Index;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class DFS extends SudokuAlgorithm {
    @Override
    public LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Index unassigned, int value) {
        return new LinkedList<>();
    }
}
