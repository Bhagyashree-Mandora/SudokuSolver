package test.usu.algorithms;

import com.google.common.collect.Lists;
import main.usu.algorithms.DFS;
import main.usu.algorithms.SudokuAlgorithm;
import main.usu.model.Position;
import main.usu.model.SudokuPuzzle;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DFSTest {
    SudokuPuzzle puzzle;
    SudokuAlgorithm dfs;

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
        dfs = new DFS();
    }

    @Test
    public void testThereAreNoInferencesInDFS() {
        assertEquals(Lists.newArrayList(), dfs.getInferences(puzzle, new Position(0, 1), "D"));
    }
}
