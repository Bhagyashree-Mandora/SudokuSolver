package main.usu.algorithms;

import main.usu.model.Position;
import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

public abstract class SudokuAlgorithm {

    private double start;
    private double stop;
    private long numberOfSteps;

    public abstract LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Position unassigned, String value);

    public SudokuPuzzle solve(SudokuPuzzle puzzle) {
        startTimer();
        SudokuPuzzle solution = backtrack(puzzle);
        stopTimer();
        return solution;
    }

    protected void stopTimer() {
        if (start != 0) {
            stop = System.currentTimeMillis();
        }
        System.out.println("Total Time Taken: " + ((stop - start) / 1000) + " secs");
        System.out.println("Number of Steps: " + numberOfSteps);
    }

    protected void startTimer() {
        start = System.currentTimeMillis();
    }

    protected SudokuPuzzle backtrack(SudokuPuzzle puzzle) {
        if (isFilledIn(puzzle)) {
            return puzzle;
        }

        Position unassigned = selectUnAssignedVariable(puzzle);
        LinkedList<String> domainValues = orderDomainValues(unassigned, puzzle);

        for (String domainValue : domainValues) {
            LinkedList<Inference> inferences = null;
            String value = domainValue;
            puzzle.set(unassigned.getRow(), unassigned.getCol(), value);
//            try {
//                sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            numberOfSteps++;

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

    protected LinkedList<String> orderDomainValues(Position var, SudokuPuzzle puzzle) {
        return puzzle.getDomain(var.getRow(), var.getCol());
    }

    public Position selectUnAssignedVariable(SudokuPuzzle puzzle) {
        for (int row = 0; row < puzzle.getPuzzleSide(); row++) {
            for (int col = 0; col < puzzle.getPuzzleSide(); col++) {
                if (puzzle.get(row, col).equals(SudokuPuzzle.NIL)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public boolean isFilledIn(SudokuPuzzle puzzle) {
        for (int row = 0; row < puzzle.getPuzzleSide(); row++) {
            for (int col = 0; col < puzzle.getPuzzleSide(); col++) {
                if (puzzle.get(row, col).equals(SudokuPuzzle.NIL)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void unapplyInferences(SudokuPuzzle puzzle, LinkedList<Inference> inferences) {
        if (inferences == null || inferences.size() == 0) {
            return;
        }

        for (Inference inference : inferences) {
            if (!puzzle.inDomain(inference.getRow(), inference.getCol(), inference.getValue())) {
                puzzle.addToDomain(inference.getRow(), inference.getCol(), inference.getValue());
            }
        }
    }

    public void applyInferences(SudokuPuzzle puzzle, LinkedList<Inference> inferences) {
        if (inferences == null || inferences.size() == 0) {
            return;
        }

        for (Inference inference : inferences) {
            puzzle.removeFromDomain(inference.getRow(), inference.getCol(), inference.getValue());
        }
    }

    public boolean isGoodSoFar(SudokuPuzzle puzzle) {
		/* Check each unit */
        for (int uRow = 0; uRow < puzzle.getUnitSide(); uRow++) {
            for (int uCol = 0; uCol < puzzle.getUnitSide(); uCol++) {
                String[] nums = new String[puzzle.getPuzzleSide()];
                int startRow = uRow * puzzle.getUnitSide();
                int startCol = uCol * puzzle.getUnitSide();
                int endRow = startRow + puzzle.getUnitSide();
                int endCol = startCol + puzzle.getUnitSide();
                int at = 0;

                for (int row = startRow; row < endRow; row++) {
                    for (int col = startCol; col < endCol; col++) {
                        nums[at++] = puzzle.get(row, col);
                    }
                }

                Arrays.sort(nums);

                for (int i = 1; i < at; i++) {
                    if (!nums[i].equals(SudokuPuzzle.NIL) && !nums[i - 1].equals(SudokuPuzzle.NIL) && nums[i].equals(nums[i - 1])) {
                        return false;
                    }
                }
            }
        }

		/* Check each row */
        for (int row = 0; row < puzzle.getPuzzleSide(); row++) {
            String[] nums = new String[puzzle.getPuzzleSide()];

            for (int col = 0; col < puzzle.getPuzzleSide(); col++) {
                nums[col] = puzzle.get(row, col);
            }

            Arrays.sort(nums);

            for (int i = 1; i < nums.length; i++) {
                if (!nums[i].equals(SudokuPuzzle.NIL) && !nums[i - 1].equals(SudokuPuzzle.NIL) && nums[i].equals(nums[i - 1])) {
                    return false;
                }
            }
        }

		/* Check each column */
        for (int col = 0; col < puzzle.getPuzzleSide(); col++) {
            String[] nums = new String[puzzle.getPuzzleSide()];

            for (int row = 0; row < puzzle.getPuzzleSide(); row++) {
                nums[row] = puzzle.get(row, col);
            }

            Arrays.sort(nums);

            for (int i = 1; i < nums.length; i++) {
                if (!nums[i].equals(SudokuPuzzle.NIL) && !nums[i - 1].equals(SudokuPuzzle.NIL) && nums[i].equals(nums[i - 1])) {
                    return false;
                }
            }
        }

        return true;
    }
}
