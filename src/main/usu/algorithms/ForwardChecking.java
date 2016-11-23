package main.usu.algorithms;

import main.usu.model.Position;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class ForwardChecking extends SudokuAlgorithm {

    public LinkedList<Inference> forwardCheckInferences(SudokuPuzzle puzzle, Position assigned, String value) {
        LinkedList<Inference> inferences = new LinkedList<Inference>();

		/* Flush domain for current square */
        for (String i : puzzle.getPossibleValues()) {
            if (!value.equals(i)) {
                addInference(puzzle, inferences, assigned.getRow(), assigned.getCol(), i);
            }
        }

		/* Flush domains in current unit */
        int startRow = (assigned.getRow() / puzzle.getUnitSide()) * puzzle.getUnitSide();
        int startCol = (assigned.getCol() / puzzle.getUnitSide()) * puzzle.getUnitSide();
        int endRow = startRow + puzzle.getUnitSide();
        int endCol = startCol + puzzle.getUnitSide();

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
        for (int r = 0; r < puzzle.getPuzzleSide(); r++) {
            if (r == assigned.getRow()) {
                continue;
            }

            if (!addInference(puzzle, inferences, r, assigned.getCol(), value)) {
                return null;
            }
        }

		/* Flush domains in current column */
        for (int c = 0; c < puzzle.getPuzzleSide(); c++) {
            if (c == assigned.getCol()) {
                continue;
            }

            if (!addInference(puzzle, inferences, assigned.getRow(), c, value)) {
                return null;
            }
        }

        return inferences;
    }

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
        return forwardCheckInferences(sudokuPuzzle, unassigned, value);
    }
}