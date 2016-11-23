package main.usu.algorithms;

import main.usu.model.Index;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class SudokuAlgorithm {

    private double start;
    private double stop;

    public abstract LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Index unassigned, int value);

    public SudokuPuzzle solve(SudokuPuzzle puzzle) {
        startTimer();
        SudokuPuzzle solution = backtrack(puzzle);
        stopTimer();
        return solution;
    }

    private void stopTimer() {
        if (start != 0) {
            stop = System.currentTimeMillis();
        }
    }

    private void startTimer() {
        start = System.currentTimeMillis();
    }

    private SudokuPuzzle backtrack(SudokuPuzzle puzzle) {
        if (isFilledIn(puzzle)) {
            return puzzle;
        }

        Index unassigned = selectUnAssignedVariable(puzzle);
        LinkedList<Integer> domainValues = orderDomainValues(unassigned, puzzle);

        for (Integer domainValue : domainValues) {
            LinkedList<Inference> inferences = null;
            int value = domainValue;
            puzzle.set(unassigned.getRow(), unassigned.getCol(), value);

            if (isGoodSoFar(puzzle)) {
                inferences = getInferences(puzzle, unassigned, value);

                if (inferences == null) {
                    continue;
                }

                applyInferences(puzzle, inferences);
                SudokuPuzzle result = backtrack(puzzle);

                if (result != null) {
                    return result;
                }
            }

            puzzle.unset(unassigned.getRow(), unassigned.getCol());
            unapplyInferences(puzzle, inferences);
        }

        return null;
    }

    private LinkedList<Integer> orderDomainValues(Index var, SudokuPuzzle puzzle) {
        return puzzle.getDomain(var.getRow(), var.getCol());
    }

    private Index selectUnAssignedVariable(SudokuPuzzle puzzle) {
        for (int row = 0; row < puzzle.puzzleSide; row++) {
            for (int col = 0; col < puzzle.puzzleSide; col++) {
                if (puzzle.get(row, col) == 0) {
                    return new Index(row, col);
                }
            }
        }
        return null;
    }

    private boolean isFilledIn(SudokuPuzzle puzzle) {
        for (int row = 0; row < puzzle.puzzleSide; row++) {
            for (int col = 0; col < puzzle.puzzleSide; col++) {
                if (puzzle.get(row, col) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void unapplyInferences(SudokuPuzzle puzzle, LinkedList<Inference> inferences) {
        if (inferences == null || inferences.size() == 0) {
            return;
        }

        for (Inference inference : inferences) {
            if (!puzzle.inDomain(inference.getRow(), inference.getCol(), inference.getExclusion())) {
                puzzle.addToDomain(inference.getRow(), inference.getCol(), inference.getExclusion());
            }
        }
    }

    private void applyInferences(SudokuPuzzle puzzle, LinkedList<Inference> inferences) {
        if (inferences == null || inferences.size() == 0) {
            return;
        }

        for (Inference inference : inferences) {
            puzzle.removeFromDomain(inference.getRow(), inference.getCol(), inference.getExclusion());
        }
    }

    private boolean isGoodSoFar(SudokuPuzzle puzzle) {
		/* Check each unit */
        for (int uRow = 0; uRow < puzzle.unitSide; uRow++) {
            for (int uCol = 0; uCol < puzzle.unitSide; uCol++) {
                int[] nums = new int[9];
                int startRow = uRow * puzzle.unitSide;
                int startCol = uCol * puzzle.unitSide;
                int endRow = startRow + puzzle.unitSide;
                int endCol = startCol + puzzle.unitSide;
                int at = 0;

                for (int row = startRow; row < endRow; row++) {
                    for (int col = startCol; col < endCol; col++) {
                        nums[at++] = puzzle.get(row, col);
                    }
                }

                Arrays.sort(nums);

                for (int i = 1; i < at; i++) {
                    if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
                        return false;
                    }
                }
            }
        }

		/* Check each row */
        for (int row = 0; row < puzzle.puzzleSide; row++) {
            int[] nums = new int[9];

            for (int col = 0; col < puzzle.puzzleSide; col++) {
                nums[col] = puzzle.get(row, col);
            }

            Arrays.sort(nums);

            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
                    return false;
                }
            }
        }

		/* Check each column */
        for (int col = 0; col < puzzle.puzzleSide; col++) {
            int[] nums = new int[9];

            for (int row = 0; row < puzzle.puzzleSide; row++) {
                nums[row] = puzzle.get(row, col);
            }

            Arrays.sort(nums);

            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
                    return false;
                }
            }
        }

        return true;
    }

}
