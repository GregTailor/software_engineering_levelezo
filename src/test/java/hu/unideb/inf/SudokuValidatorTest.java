package hu.unideb.inf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuValidatorTest {

    private SudokuBoard board;

    @BeforeEach
    public void setup(){
        int[][] initialBoard = new int[][]{
                {0,0,0,4,0,0,0,9,0},
                {6,0,7,0,0,0,8,0,4},
                {0,1,0,7,0,9,0,0,3},
                {9,0,1,0,7,0,0,3,0},
                {0,0,2,0,0,0,9,0,0},
                {0,5,0,0,4,0,1,0,7},
                {3,0,0,5,0,2,0,7,0},
                {4,0,6,0,0,0,3,0,1},
                {0,7,0,0,0,4,0,0,0}
        };
        board = new SudokuBoard(initialBoard);
    }

    @Test
    public void valuesFitInRowDefault(){
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertTrue(sudokuValidator.valuesFitInRow(0));
    }

    @Test
    public void valuesFitInRowFilledCorrectly() throws Exception {
        board.modifyCell(1, 0, 0);
        board.modifyCell(2, 0, 1);
        board.modifyCell(3, 0, 2);
        board.modifyCell(5, 0, 4);
        board.modifyCell(6, 0, 5);
        board.modifyCell(7, 0, 6);
        board.modifyCell(8, 0, 8);
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertTrue(sudokuValidator.valuesFitInRow(0));
    }

    @Test
    public void valuesFitInRowFilledInorrectly() throws Exception {
        board.modifyCell(4, 0, 0);
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertFalse(sudokuValidator.valuesFitInRow(0));
    }


    @Test
    public void valuesFitInColumnDefault(){
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertTrue(sudokuValidator.valuesFitInColumn(0));
    }

    @Test
    public void valuesFitInColumnFilledCorrectly() throws Exception {
        board.modifyCell(1, 0, 0);
        board.modifyCell(2, 2, 0);
        board.modifyCell(7, 4, 0);
        board.modifyCell(5, 5, 0);
        board.modifyCell(8, 8, 0);
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertTrue(sudokuValidator.valuesFitInColumn(0));
    }

    @Test
    public void valuesFitInColumnFilledInorrectly() throws Exception {
        board.modifyCell(6, 0, 0);
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertFalse(sudokuValidator.valuesFitInColumn(0));
    }

    @Test
    public void valuesFitInSquareDefault(){
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertTrue(sudokuValidator.valuesFitInSquare(0, 0));
    }

    @Test
    public void valuesFitInSquareFilledCorrectly() throws Exception {
        board.modifyCell(2, 0, 0);
        board.modifyCell(3, 0, 1);
        board.modifyCell(4, 0, 2);
        board.modifyCell(5, 1, 1);
        board.modifyCell(8, 2, 0);
        board.modifyCell(9, 2, 2);
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertTrue(sudokuValidator.valuesFitInSquare(0, 0));
    }

    @Test
    public void valuesFitInSquareFilledInorrectly() throws Exception {
        board.modifyCell(6, 0, 0);
        SudokuValidator sudokuValidator = new SudokuValidator(board);
        assertFalse(sudokuValidator.valuesFitInSquare(0, 0));
    }

}