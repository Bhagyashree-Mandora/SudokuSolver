package test.usu.model;

import com.google.common.collect.Lists;
import main.usu.model.SudokuPuzzle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class SudokuPuzzleTest {
    SudokuPuzzle puzzle;

    @Before
    public void setup() {
        String[][] stringPuzzle = {
                {"B", "-", "C", "A"},
                {"A", "C", "-", "D"},
                {"C", "A", "D", "-"},
                {"-", "B", "A", "C"},
        };
        List<String> domain = Lists.newArrayList("A", "B", "C", "D");
        puzzle = new SudokuPuzzle(stringPuzzle, domain);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidValueInInput() {
        String[][] stringPuzzle = {
                {"Z", "-", "C", "A"},
                {"A", "C", "-", "D"},
                {"C", "A", "D", "-"},
                {"-", "B", "A", "C"},
        };
        List<String> domain = Lists.newArrayList("A", "B", "C", "D");
        puzzle = new SudokuPuzzle(stringPuzzle, domain);
    }

    @Test
    public void testBasicValuesAreSetProperly() {
        assertEquals(2, puzzle.getUnitSide());
        assertEquals(4, puzzle.getPuzzleSide());
        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getPossibleValues());
    }

    @Test
    public void testDomainForCellWithInputValue() {
        assertEquals(Lists.newArrayList("B"), puzzle.getDomain(0, 0));
    }

    @Test
    public void testDomainForCellWithNoInputValue() {
        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testValueOfAnyCell() {
        assertEquals("A", puzzle.get(0, 3));
        assertEquals("C", puzzle.get(1, 1));
    }

    @Test
    public void testSetPuzzleValue() {
        puzzle.set(0, 1, "D");
        assertEquals("D", puzzle.get(0, 1));
        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testUnsetPuzzleValue() {
        puzzle.unset(0, 0);
        assertEquals("-", puzzle.get(0, 0));
        assertEquals(Lists.newArrayList("A", "B", "C", "D"), puzzle.getDomain(0, 0));
    }

    @Test
    public void testRemoveFromDomainWhenValuePresent() {
        puzzle.removeFromDomain(0, 1, "A");
        assertEquals(Lists.newArrayList("B", "C", "D"), puzzle.getDomain(0, 1));
    }

    @Test
    public void testRemoveFromDomainWhenValueAbsent() {
        puzzle.removeFromDomain(0, 0, "C");
        assertEquals(Lists.newArrayList("B"), puzzle.getDomain(0, 0));
    }

    @Test
    public void testAddToDomain() {
        puzzle.addToDomain(0, 0, "C");
        assertEquals(Lists.newArrayList("B", "C"), puzzle.getDomain(0, 0));
    }

    @Test
    public void testGetDomainSize() {
        assertEquals(1, puzzle.getDomainLength(0, 0));
        assertEquals(4, puzzle.getDomainLength(0, 1));
    }

    @Test
    public void testInDomainWhenValueIsPresentInDomain() {
        assertTrue(puzzle.inDomain(0, 0, "B"));
    }

    @Test
    public void testInDomainWhenValueIsAbsentFromDomain() {
        assertFalse(puzzle.inDomain(0, 0, "A"));
    }
}
