package test.usu.algorithms;

import com.google.common.collect.Lists;
import main.usu.algorithms.DFS;

import main.usu.algorithms.SudokuAlgorithm;
import main.usu.model.Inference;
import main.usu.model.Position;
import main.usu.model.SudokuPuzzle;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

public class SudokuAlgorithmTest {
    SudokuPuzzle puzzle;
    SudokuAlgorithm algorithm;

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
        algorithm = spy(new DFS());
    }

    @Test
    public void testIfFilledInForFilledPuzzle() {
        String[][] stringPuzzle = {{"A"},};
        List<String> domain = Lists.newArrayList("A");
        puzzle = new SudokuPuzzle(stringPuzzle, domain);

        assertTrue(algorithm.isFilledIn(puzzle));
    }

    @Test
    public void testIfFilledInForUnfilledPuzzle() {
        String[][] stringPuzzle = {{"-"},};
        List<String> domain = Lists.newArrayList("A");
        puzzle = new SudokuPuzzle(stringPuzzle, domain);

        assertFalse(algorithm.isFilledIn(puzzle));
    }

    @Test
    public void testIsGoodSoFarForCorrectAddition() {
        puzzle.set(0, 1, "D");
        assertTrue(algorithm.isGoodSoFar(puzzle));
    }

    @Test
    public void testIsGoodSoFarForIncorrectRowAddition() {
        puzzle.set(0, 1, "C");
        assertFalse(algorithm.isGoodSoFar(puzzle));
    }

    @Test
    public void testIsGoodSoFarForIncorrectColumnAddition() {
        puzzle.set(1, 1, "B");
        assertFalse(algorithm.isGoodSoFar(puzzle));
    }

    @Test
    public void testIsGoodSoFarForIncorrectBlockAddition() {
        puzzle.set(1, 3, "C");
        assertFalse(algorithm.isGoodSoFar(puzzle));
    }

    @Test
    public void testSelectUnasignedSouldGiveAProperCell() {
        Position unassignedPos = algorithm.selectUnAssignedVariable(puzzle);
        assertEquals(0, unassignedPos.getRow());
        assertEquals(1, unassignedPos.getCol());
    }

    @Test
    public void testSelectUnasignedSouldGiveNullInNonePresent() {
        String[][] stringPuzzle = {{"A"},};
        List<String> domain = Lists.newArrayList("A");
        puzzle = new SudokuPuzzle(stringPuzzle, domain);

        assertNull(algorithm.selectUnAssignedVariable(puzzle));
    }

    @Test
    public void testApplyInferences() {
        LinkedList<Inference> inferences = new LinkedList<>();
        inferences.add(new Inference(0, 1, "A"));

        algorithm.applyInferences(puzzle, inferences);

        assertEquals(Lists.newArrayList("B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testApplyInferencesIfNull() {
        algorithm.applyInferences(puzzle, null);

        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testApplyInferencesIfEmpty() {
        LinkedList<Inference> inferences = new LinkedList<>();

        algorithm.applyInferences(puzzle, inferences);

        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testUnapplyInferences() {
        LinkedList<Inference> inferences = new LinkedList<>();
        inferences.add(new Inference(0, 1, "A"));

        algorithm.applyInferences(puzzle, inferences);
        algorithm.unapplyInferences(puzzle, inferences);

        assertEquals(Lists.newArrayList("B", "C", "D", "A"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testUnapplyInferencesForEmpty() {
        LinkedList<Inference> inferences = new LinkedList<>();

        algorithm.unapplyInferences(puzzle, inferences);

        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testUnapplyInferencesForNull() {
        algorithm.unapplyInferences(puzzle, null);

        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 1));
    }
}
