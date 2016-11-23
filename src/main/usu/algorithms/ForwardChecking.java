package main.usu.algorithms;

import main.usu.model.Index;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class ForwardChecking extends SudokuAlgorithm {

    public LinkedList<Inference> forwardCheckInferences(SudokuPuzzle puzzle, Index assigned, int value) {
        LinkedList<Inference> inferences = new LinkedList<Inference>();

		/* Flush domain for current square */
        for (int i = 1; i <= 9; i++) {
            if (i != value) {
                addInference(puzzle, inferences, assigned.getRow(), assigned.getCol(), i);
            }
        }

		/* Flush domains in current unit */
        int startRow = (assigned.getRow() / puzzle.unitSide) * puzzle.unitSide;
        int startCol = (assigned.getCol() / puzzle.unitSide) * puzzle.unitSide;
        int endRow = startRow + puzzle.unitSide;
        int endCol = startCol + puzzle.unitSide;

        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (row == assigned.getRow() || col == assigned.getCol()) {
                    continue;
                }

                if (!addInference(puzzle, inferences, row, col, value)) {
                    return null;
                }
            }
        }

		/* Flush domains in current row */
        for (int r = 0; r < puzzle.puzzleSide; r++) {
            if (r == assigned.getRow()) {
                continue;
            }

            if (!addInference(puzzle, inferences, r, assigned.getCol(), value)) {
                return null;
            }
        }

		/* Flush domains in current column */
        for (int c = 0; c < puzzle.puzzleSide; c++) {
            if (c == assigned.getCol()) {
                continue;
            }

            if (!addInference(puzzle, inferences, assigned.getRow(), c, value)) {
                return null;
            }
        }

        return inferences;
    }

    private boolean addInference(SudokuPuzzle puzzle, LinkedList<Inference> inferences, int row, int col, int value) {
        Inference newInference = new Inference(row, col, value);

        if (!inferences.contains(newInference) && puzzle.inDomain(row, col, value)) {
            if (puzzle.getDomainLength(row, col) == 1) {
                return false;
            }

            inferences.add(newInference);
        }
        return true;
    }

    @Override
    public LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Index unassigned, int value) {
        return forwardCheckInferences(sudokuPuzzle, unassigned, value);
    }
}