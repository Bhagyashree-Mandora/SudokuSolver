package main.usu.algorithms;

import main.usu.model.Index;
import main.usu.model.Inference;
import main.usu.model.PriorityQueue;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class AC3 extends SudokuAlgorithm {

    private boolean solveAC3(SudokuPuzzle puzzle) {
        PriorityQueue<Index[]> arcs = new PriorityQueue<Index[]>();
        prepareQueue(puzzle, arcs);

        while (arcs.size() > 0) {
            Index[] edge = arcs.dequeue();

            if (!revise(puzzle, edge[0], edge[1])) {
                continue;
            }

            int assignedRow = edge[0].getRow();
            int assignedCol = edge[0].getCol();
            int otherRow = edge[1].getRow();
            int otherCol = edge[1].getCol();

            if (puzzle.getDomainLength(assignedRow, assignedCol) == 0) {
                return false;
            }

			/* Queue squares in current unit */
            int startRow = (assignedRow / puzzle.unitSide) * puzzle.unitSide;
            int startCol = (assignedCol / puzzle.unitSide) * puzzle.unitSide;
            int endRow = startRow + puzzle.unitSide;
            int endCol = startCol + puzzle.unitSide;

            for (int row = startRow; row < endRow; row++) {
                for (int col = startCol; col < endCol; col++) {
                    if (row != assignedRow && col != assignedCol && row != otherRow && col != otherCol) {
                        arcs.enqueue(new Index[] { new Index(row, col), new Index(assignedRow, assignedCol) });
                    }
                }
            }

			/* Queue squares in current row */
            for (int row = 0; row < puzzle.puzzleSide; row++) {
                if (row != assignedRow && row != otherRow) {
                    arcs.enqueue(new Index[] { new Index(row, assignedCol), new Index(assignedRow, assignedCol) });
                }
            }

			/* Queue squares in current column */
            for (int col = 0; col < puzzle.puzzleSide; col++) {
                if (col != assignedCol && col != otherCol) {
                    arcs.enqueue(new Index[] { new Index(assignedRow, col), new Index(assignedRow, assignedCol) });
                }
            }
        }

        return true;
    }

    private boolean revise(SudokuPuzzle puzzle, Index i, Index j) {
        boolean revised = false;

        LinkedList<String> domainI = puzzle.getDomainCopy(i.getRow(), i.getCol());
        LinkedList<String> domainJ = puzzle.getDomain(j.getRow(), j.getCol());

        for (String x : domainI) {
            boolean satisfiable = false;

            for (String y : domainJ) {
                if (!y.equals(x)) {
                    satisfiable = true;
                }
            }

            if (!satisfiable) {
                puzzle.removeFromDomain(i.getRow(), i.getCol(), x);
                revised = true;
            }
        }

        return revised;
    }

    private void prepareQueue(SudokuPuzzle puzzle, PriorityQueue<Index[]> arcs) {
        for (int assignedRow = 0; assignedRow < puzzle.puzzleSide; assignedRow++) {
            for (int assignedCol = 0; assignedCol < puzzle.puzzleSide; assignedCol++) {
				/* Queue squares in current unit */
                int startRow = (assignedRow / puzzle.unitSide) * puzzle.unitSide;
                int startCol = (assignedCol / puzzle.unitSide) * puzzle.unitSide;
                int endRow = startRow + puzzle.unitSide;
                int endCol = startCol + puzzle.unitSide;

                for (int row = startRow; row < endRow; row++) {
                    for (int col = startCol; col < endCol; col++) {
                        if (row != assignedRow && col != assignedCol) {
                            arcs.enqueue(new Index[] { new Index(row, col), new Index(assignedRow, assignedCol) });
                        }
                    }
                }

				/* Queue squares in current row */
                for (int row = 0; row < puzzle.puzzleSide; row++) {
                    if (row != assignedRow) {
                        arcs.enqueue(new Index[] { new Index(row, assignedCol), new Index(assignedRow, assignedCol) });
                    }
                }

				/* Queue squares in current column */
                for (int col = 0; col < puzzle.puzzleSide; col++) {
                    if (col != assignedCol) {
                        arcs.enqueue(new Index[] { new Index(assignedRow, col), new Index(assignedRow, assignedCol) });
                    }
                }
            }
        }
    }

    @Override
    public LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Index unassigned, String value) {
        if(solveAC3(sudokuPuzzle)) {
            return new LinkedList<>();
        }
        return null;
    }
}