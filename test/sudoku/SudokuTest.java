package sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for Sudoku class
 * @author Johannes Bastmark
 * @author Szymon Stypa
 * @author Axel Domell
 *
 * @version 1.0
 * @see <a href="https://github.com/bastmark/Sudoku-solver">Github repository</a>
 */
public class SudokuTest {
    private Sudoku sdk;

    @Before
    public void setUp() throws Exception {
        sdk = new Sudoku();
    }

    @After
    public void tearDown() throws Exception {
        sdk = null;
    }

    @Test
    public void testBoard(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                assertEquals("Testing empty board with 0's", 0, sdk.get(i, j));
            }
        }
    }

    @Test
    public void testInsertAndGet() {
        assertTrue("Testing insert",sdk.insert(5, 1,1));
        assertEquals("Testing get on insert", 5, sdk.get(1,1));
        assertFalse("Testing insert out of bounds", sdk.insert(5, 10,10));
        assertEquals("Testing get out of bounds", -1, sdk.get(10,10));
    }

    @Test
    public void testSolved() {
        int[][] solvedBoard = {
                {8, 1, 2, 7, 5, 3, 6, 4, 9},
                {9, 4, 3, 6, 8, 2, 1, 7, 5},
                {6, 7, 5, 4, 9, 1, 2, 8, 3,},
                {1, 5, 4, 2, 3, 7, 8, 9, 6},
                {3, 6, 9, 8, 4, 5, 7, 2, 1},
                {2, 8, 7, 1, 6, 9, 5, 3, 4},
                {5, 2, 1, 9, 7, 4, 3, 6, 8},
                {4, 3, 8, 5, 2, 6, 9, 1, 7},
                {7, 9, 6, 3, 1, 8, 4, 5, 2},
        };

        sdk = new Sudoku(solvedBoard);

        assertTrue("Testing solved on solved board", sdk.solved());
    }

    @Test
    public void testValid(){
        int[][] validBoard = {
                {0, 1, 2, 7, 5, 3, 6, 0, 9},
                {9, 0, 3, 6, 8, 2, 1, 7, 5},
                {6, 7, 5, 4, 9, 1, 2, 8, 3,},
                {1, 5, 4, 2, 3, 7, 8, 9, 6},
                {3, 6, 9, 8, 4, 5, 7, 2, 1},
                {2, 8, 7, 1, 6, 9, 5, 3, 4},
                {5, 2, 1, 0, 7, 4, 3, 6, 8},
                {4, 3, 8, 5, 2, 6, 9, 1, 7},
                {7, 9, 6, 3, 1, 8, 0, 5, 2},
        };

        sdk = new Sudoku(validBoard);

        assertTrue("Testing valid on valid board", sdk.valid());
    }

    @Test
    public void testSolve(){
        int[][] solvedBoard = {
                {8, 1, 2, 7, 5, 3, 6, 4, 9},
                {9, 4, 3, 6, 8, 2, 1, 7, 5},
                {6, 7, 5, 4, 9, 1, 2, 8, 3,},
                {1, 5, 4, 2, 3, 7, 8, 9, 6},
                {3, 6, 9, 8, 4, 5, 7, 2, 1},
                {2, 8, 7, 1, 6, 9, 5, 3, 4},
                {5, 2, 1, 9, 7, 4, 3, 6, 8},
                {4, 3, 8, 5, 2, 6, 9, 1, 7},
                {7, 9, 6, 3, 1, 8, 4, 5, 2},
        };

        //0's added at row 2 column 5 and row 5 column 3.
        int[][] unSolvedBoard = {
                {8, 1, 2, 7, 5, 3, 6, 4, 9},
                {9, 4, 3, 6, 0, 2, 1, 7, 5},
                {6, 7, 5, 4, 9, 1, 2, 8, 3,},
                {1, 5, 4, 2, 3, 7, 8, 9, 6},
                {3, 6, 0, 8, 4, 5, 7, 2, 1},
                {2, 8, 7, 1, 6, 9, 5, 3, 4},
                {5, 2, 1, 9, 7, 4, 3, 6, 8},
                {4, 3, 8, 5, 2, 6, 9, 1, 7},
                {7, 9, 6, 3, 1, 8, 4, 5, 2},
        };

        sdk = new Sudoku(unSolvedBoard);
        assertTrue("Test solve method to return true", sdk.solve());
        assertArrayEquals(solvedBoard, sdk.getBoard());

    }
}