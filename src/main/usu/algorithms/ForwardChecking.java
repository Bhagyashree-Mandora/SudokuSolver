package main.usu.algorithms;

import main.usu.model.Position;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class ForwardChecking extends SudokuAlgorithm {

    private boolean addInference(SudokuPuzzle puzzle, LinkedList<Inference> inferences, int row, int col, String value) {
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
    public LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Position unassigned, String value) {
        LinkedList<Inference> inferences = new LinkedList<Inference>();

		/* Flush domain for current square */
        for (String i : sudokuPuzzle.getPossibleValues()) {
            if (!value.equals(i)) {
                addInference(sudokuPuzzle, inferences, unassigned.getRow(), unassigned.getCol(), i);
            }
        }

		/* Flush domains in current unit */
        int startRow = (unassigned.getRow() / sudokuPuzzle.getUnitSide()) * sudokuPuzzle.getUnitSide();
        int startCol = (unassigned.getCol() / sudokuPuzzle.getUnitSide()) * sudokuPuzzle.getUnitSide();
        int endRow = startRow + sudokuPuzzle.getUnitSide();
        int endCol = startCol + sudokuPuzzle.getUnitSide();

        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (row == unassigned.getRow() || col == unassigned.getCol()) {
                    continue;
                }

                if (!addInference(sudokuPuzzle, inferences, row, col, value)) {
                    return null;
                }
            }
        }

		/* Flush domains in current row */
        for (int r = 0; r < sudokuPuzzle.getPuzzleSide(); r++) {
            if (r == unassigned.getRow()) {
                continue;
            }

            if (!addInference(sudokuPuzzle, inferences, r, unassigned.getCol(), value)) {
                return null;
            }
        }

		/* Flush domains in current column */
        for (int c = 0; c < sudokuPuzzle.getPuzzleSide(); c++) {
            if (c == unassigned.getCol()) {
                continue;
            }

            if (!addInference(sudokuPuzzle, inferences, unassigned.getRow(), c, value)) {
                return null;
            }
        }

        return inferences;
    }
}