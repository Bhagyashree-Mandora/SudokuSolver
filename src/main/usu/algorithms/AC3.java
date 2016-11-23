package main.usu.algorithms;

import main.usu.model.Position;
import main.usu.model.Inference;
import main.usu.utils.PriorityQueue;
import main.usu.model.SudokuPuzzle;

import java.util.LinkedList;

public class AC3 extends SudokuAlgorithm {

    private boolean solveAC3(SudokuPuzzle puzzle) {
        PriorityQueue<Position[]> arcs = new PriorityQueue<Position[]>();
        prepareQueue(puzzle, arcs);

        while (arcs.size() > 0) {
            Position[] edge = arcs.dequeue();

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
            int startRow = (assignedRow / puzzle.getUnitSide()) * puzzle.getUnitSide();
            int startCol = (assignedCol / puzzle.getUnitSide()) * puzzle.getUnitSide();
            int endRow = startRow + puzzle.getUnitSide();
            int endCol = startCol + puzzle.getUnitSide();

            for (int row = startRow; row < endRow; row++) {
                for (int col = startCol; col < endCol; col++) {
                    if (row != assignedRow && col != assignedCol && row != otherRow && col != otherCol) {
                        arcs.enqueue(new Position[] { new Position(row, col), new Position(assignedRow, assignedCol) });
                    }
                }
            }

			/* Queue squares in current row */
            for (int row = 0; row < puzzle.getPuzzleSide(); row++) {
                if (row != assignedRow && row != otherRow) {
                    arcs.enqueue(new Position[] { new Position(row, assignedCol), new Position(assignedRow, assignedCol) });
                }
            }

			/* Queue squares in current column */
            for (int col = 0; col < puzzle.getPuzzleSide(); col++) {
                if (col != assignedCol && col != otherCol) {
                    arcs.enqueue(new Position[] { new Position(assignedRow, col), new Position(assignedRow, assignedCol) });
                }
            }
        }

        return true;
    }

    private boolean revise(SudokuPuzzle puzzle, Position i, Position j) {
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

    private void prepareQueue(SudokuPuzzle puzzle, PriorityQueue<Position[]> arcs) {
        for (int assignedRow = 0; assignedRow < puzzle.getPuzzleSide(); assignedRow++) {
            for (int assignedCol = 0; assignedCol < puzzle.getPuzzleSide(); assignedCol++) {
				/* Queue squares in current unit */
                int startRow = (assignedRow / puzzle.getUnitSide()) * puzzle.getUnitSide();
                int startCol = (assignedCol / puzzle.getUnitSide()) * puzzle.getUnitSide();
                int endRow = startRow + puzzle.getUnitSide();
                int endCol = startCol + puzzle.getUnitSide();

                for (int row = startRow; row < endRow; row++) {
                    for (int col = startCol; col < endCol; col++) {
                        if (row != assignedRow && col != assignedCol) {
                            arcs.enqueue(new Position[] { new Position(row, col), new Position(assignedRow, assignedCol) });
                        }
                    }
                }

				/* Queue squares in current row */
                for (int row = 0; row < puzzle.getPuzzleSide(); row++) {
                    if (row != assignedRow) {
                        arcs.enqueue(new Position[] { new Position(row, assignedCol), new Position(assignedRow, assignedCol) });
                    }
                }

				/* Queue squares in current column */
                for (int col = 0; col < puzzle.getPuzzleSide(); col++) {
                    if (col != assignedCol) {
                        arcs.enqueue(new Position[] { new Position(assignedRow, col), new Position(assignedRow, assignedCol) });
                    }
                }
            }
        }
    }

    @Override
    public LinkedList<Inference> getInferences(SudokuPuzzle sudokuPuzzle, Position unassigned, String value) {
        if(solveAC3(sudokuPuzzle)) {
            return new LinkedList<>();
        }
        return null;
    }
}