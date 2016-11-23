package main.usu.model;

import java.util.*;

public class SudokuPuzzle {
	private int unitSide = 0;
	private int puzzleSide = 0;
	private PuzzleValue[][] puzzle;
	private List<String> possibleValues = new ArrayList<>();

	private class PuzzleValue {
		String value;
		LinkedList<String> domain;

		public PuzzleValue(String num, List<String> possibleValues) {
			this.makeValue(num, possibleValues, false);
		}

		public PuzzleValue(String num, List<String> possibleValues, boolean entireDomain) {
			this.makeValue(num, possibleValues, true);
		}

		public void makeValue(String num, List<String> possibleValues, boolean entireDomain) {
			this.value = num;
			this.domain = new LinkedList<String>();

			if (entireDomain) {
				this.domain.add(num);
			} else {
				this.domain.addAll(possibleValues);
			}
		}

		public String toString() {
			return this.value + "";
		}
	}

	public int getUnitSide() {
		return unitSide;
	}

	public int getPuzzleSide() {
		return puzzleSide;
	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	private PuzzleValue nil() {
		return new PuzzleValue("-", this.possibleValues);
	}

	private void makePuzzle() {
		this.puzzle = new PuzzleValue[puzzleSide][];
		for (int r = 0; r < puzzleSide; r++) {
			PuzzleValue[] row = new PuzzleValue[puzzleSide];
			for (int c = 0; c < puzzleSide; c++) {
				row[c] = nil();
			}
			this.puzzle[r] = row;
		}
	}

	public SudokuPuzzle(String[][] puzzle, List<String> possibleValues) {
		this.possibleValues = possibleValues;
		this.puzzleSide = puzzle.length;
		this.unitSide = (int) Math.sqrt(puzzle.length);
		this.makePuzzle();

		for (int r = 0; r < puzzle.length; r++) {
			for (int c = 0; c < puzzle.length; c++) {
				if (!puzzle[r][c].equals("-")) {
					this.puzzle[r][c] = new PuzzleValue(puzzle[r][c], possibleValues, true);
				}
			}
		}
	}

	public void print() {
		for (int r = 0; r < this.puzzleSide; r++) {
			for (int c = 0; c < this.puzzleSide; c++) {
				System.out.print(this.puzzle[r][c].toString() + " ");
			}
			System.out.println();
		}
	}

	public String get(int row, int col) {
		return this.puzzle[row][col].value;
	}

	public LinkedList<String> getDomain(int row, int col) {
		return this.puzzle[row][col].domain;
	}

	public void set(int row, int col, String value) {
		this.puzzle[row][col] = new PuzzleValue(value, this.possibleValues);
	}

	public void unset(int row, int col) {
		this.puzzle[row][col] = nil();
	}

	public LinkedList<String> getDomainCopy(int row, int col) {
		LinkedList<String> domain = this.getDomain(row, col);
		LinkedList<String> domainCopy = new LinkedList<String>();

		for (int i = 0; i < domain.size(); i++) {
			domainCopy.add(domain.get(i));
		}

		return domainCopy;
	}

	public void removeFromDomain(int row, int col, String value) {
		this.puzzle[row][col].domain.remove(value);
	}

	public void addToDomain(int row, int col, String value) {
		this.puzzle[row][col].domain.add(value);
	}

	public boolean inDomain(int row, int col, String value) {
		return this.puzzle[row][col].domain.contains(value);
	}

	public int getDomainLength(int row, int col) {
		return this.puzzle[row][col].domain.size();
	}
}