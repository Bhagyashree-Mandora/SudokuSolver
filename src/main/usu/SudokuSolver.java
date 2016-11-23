package main.usu;

import main.usu.algorithms.AC3;
import main.usu.algorithms.DFS;
import main.usu.algorithms.ForwardChecking;
import main.usu.algorithms.SudokuAlgorithm;
import main.usu.model.SudokuPuzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

public class SudokuSolver {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter type of algorithm (DFS/ Forward Checking/ AC3): ");
        String type = scanner.next();
        System.out.print("Enter input file name: ");
        String inputFileName = scanner.next();
        System.out.print("Enter output file name: ");
        String outputFileName = scanner.next();

        int [][] puzzle = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFileName));

            int side = Integer.valueOf(br.readLine());
            String[] domain = br.readLine().split(" ");

            String line = br.readLine();
            puzzle = new int[side][];
            puzzle[0] = readRow(line, side);

            int rowNum = 1;
            while ((line = br.readLine()) != null) {
                if (rowNum >= side) {
                    System.out.println("Incorrect Input");
                    System.exit(1);
                }
                puzzle[rowNum++] = readRow(line, side);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SudokuPuzzle sudokuPuzzle = new SudokuPuzzle(puzzle);

        SudokuAlgorithm algorithm = null;

        if (type.equalsIgnoreCase("DFS")) {
            algorithm = new DFS();
        } else if (type.equalsIgnoreCase("Forward Checking")) {
            algorithm = new ForwardChecking();
        } else if (type.equalsIgnoreCase("AC3")) {
            algorithm = new AC3();
        } else {
            System.out.println("Incorrect algorithm");
            System.exit(1);
        }

        SudokuPuzzle solution = algorithm.solve(sudokuPuzzle);

        if (solution == null) {
            System.out.println("No solution");
        } else {
            solution.print();
        }
    }

    private static int[] readRow(String line, int side) {
        int[] row = new int[side];
        String[] elements = line.split(" ");
        if (elements.length != side) {
            System.out.println("Incorrect Input");
            System.exit(1);
        }
        for (int i = 0; i < elements.length; i++) {
            row[i] = Integer.valueOf(elements[i]);
        }
        return row;
    }
}
