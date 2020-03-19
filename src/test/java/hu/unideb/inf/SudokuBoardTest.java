package hu.unideb.inf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

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
    public void modifyCellDefault() throws Exception {
        board.modifyCell(1, 0, 0);
        int[] expectedRow = {1,0,0,0,0,0,0,0,0};
        assertArrayEquals(expectedRow, board.getPlayerBoard()[0]);
    }

    @Test
    public void modifyCellConflictWithInitialBoard() throws Exception {
        board.modifyCell(1, 0, 3);
        int[] expectedRow = {0,0,0,0,0,0,0,0,0};
        assertArrayEquals(expectedRow, board.getPlayerBoard()[0]);
    }

    @Test
    public void modifyCellInvalidValue(){
        assertThrows(Exception.class, () -> board.modifyCell(0, 0, 0));
    }

    @Test
    public void modifyCellReplaceValue() throws Exception {
        board.modifyCell(1, 0, 0);
        board.modifyCell(2, 0, 0);
        int[] expectedRow = {2,0,0,0,0,0,0,0,0};
        assertArrayEquals(expectedRow, board.getPlayerBoard()[0]);
    }

    @Test
    public void modifyCellRemoveValue() throws Exception {
        board.modifyCell(1, 0, 0);
        board.modifyCell(1, 0, 0);
        int[] expectedRow = {0,0,0,0,0,0,0,0,0};
        assertArrayEquals(expectedRow, board.getPlayerBoard()[0]);
    }

}