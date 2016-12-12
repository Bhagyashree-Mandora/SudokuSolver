package main.usu.view;

import main.usu.model.Inference;
import main.usu.model.SudokuPuzzle;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class SudokuObserver implements Observer {
    SudokuPuzzle subject;
    SudokuLayout layout;

    public SudokuObserver(SudokuPuzzle subject) {
        this.subject = subject;
        subject.addObserver(this);
        initializeLayout();
    }

    @Override
    public void update(Observable o, Object arg) {
        Inference argument = (Inference) arg;
        layout.updateCellValue(argument.getRow(), argument.getCol(), argument.getValue());
    }

    private void initializeLayout() {
        layout = new SudokuLayout("Sudoku");
        layout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layout.addComponentsToPane(layout.getContentPane(),subject);
        layout.pack();
        layout.setVisible(true);
    }
}
