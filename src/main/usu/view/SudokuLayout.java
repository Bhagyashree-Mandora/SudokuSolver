package main.usu.view;

import main.usu.model.SudokuPuzzle;

import java.awt.*;
import javax.swing.*;

public class SudokuLayout extends JFrame {
    private GridLayout grid;
    private JPanel panel;
    private SudokuPuzzle puzzle;

    public SudokuLayout(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane, SudokuPuzzle puzzle) {
        this.panel = new JPanel();
        this.grid = new GridLayout(puzzle.getPuzzleSide(), puzzle.getPuzzleSide());
        this.puzzle = puzzle;
        panel.setLayout(grid);

        for (int i=0; i< puzzle.getPuzzleSide(); i++) {
            for (int j = 0; j < puzzle.getPuzzleSide(); j++) {
                panel.add(new JButton(puzzle.get(i,j)));
            }
        }

        pane.add(panel, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
    }

    public void updateCellValue(int row, int col, String value) {
        JButton cell = (JButton) panel.getComponent(row*puzzle.getPuzzleSide() + col);
        cell.setForeground(Color.BLUE);
        cell.setText(value);
    }
}