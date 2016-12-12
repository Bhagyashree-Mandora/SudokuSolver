package test.usu.algorithms;

import com.google.common.collect.Lists;
import main.usu.algorithms.DFS;
import main.usu.algorithms.ForwardChecking;
import main.usu.algorithms.SudokuAlgorithm;
import main.usu.model.Inference;
import main.usu.model.Position;
import main.usu.model.SudokuPuzzle;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ForwardCheckingTest {
    SudokuPuzzle puzzle;
    SudokuAlgorithm forwardChecking;

    @Before
    public void setup() {
        String[][] stringPuzzle = {
                {"B", "-", "C", "A"},
                {"A", "-", "-", "D"},
                {"C", "A", "D", "-"},
                {"-", "B", "A", "-"},
        };
        List<String> domain = Lists.newArrayList("A", "B", "C", "D");
        puzzle = new SudokuPuzzle(stringPuzzle, domain);
        forwardChecking = new ForwardChecking();
    }

    @Test
    public void testThereAreInferencesInForwardChecking() {
        LinkedList<Inference> inferences = forwardChecking.getInferences(puzzle, new Position(0, 1), "D");

        assertFalse(inferences.isEmpty());
    }

    @Test
    public void testTheInferencesInForwardChecking() {
        LinkedList<Inference> inferences = forwardChecking.getInferences(puzzle, new Position(0, 1), "D");

        assertEquals(4, inferences.size());
        assertTrue(inferences.contains(new Inference(1, 1, "D")));
    }
}
