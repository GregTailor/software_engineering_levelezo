package hu.unideb.inf;

import java.util.HashSet;
import java.util.Set;

public class SudokuValidator {

    private int[][] sodokuBoard;

    public SudokuValidator(SudokuBoard sodokuBoard){
        this.sodokuBoard = new int[9][9];
        int[][] playerBoard = sodokuBoard.getPlayerBoard();
        int[][] initialBoard = sodokuBoard.getInitial();
        for (int i = 0; i < playerBoard.length; i++){
            for (int j = 0; j < playerBoard[i].length; j++){
                if (playerBoard[i][j] != 0){
                    this.sodokuBoard[i][j] = playerBoard[i][j];
                }
                else {
                    this.sodokuBoard[i][j] = initialBoard[i][j];
                }
            }
        }
    }

    private boolean validateArrayContainsUniqueValues(int[] array){
        Set<Integer> foundNumbers = new HashSet<Integer>();
        for (int num : array) {
            if(foundNumbers.contains(num)){
                return false;
            }
            foundNumbers.add(num);
        }
        return true;
    }

    public boolean solved(){
        for (int columnIndex = 0; columnIndex < sodokuBoard.length; columnIndex++){
            if (!validateArrayContainsUniqueValues(getColumn(sodokuBoard, columnIndex))) return false;
        }
        for (int rowIndex = 0; rowIndex < sodokuBoard.length; rowIndex++){
            if (!validateArrayContainsUniqueValues(sodokuBoard[rowIndex])) return false;
        }
        for (int i = 0; i < sodokuBoard.length; i++){
            if (!validateArrayContainsUniqueValues(getSquare(sodokuBoard, (int) i / 3, i % 3))) return false;
        }
        return true;
    }

    private boolean valueFitsInRow(int[][] board, int val, int row){
        return !inArray(board[row], val);
    }

    private boolean valueFitsInColumn(int[][] board, int val, int col){
        return !inArray(getColumn(board, col), val);
    }

    private boolean valueFitsInSquare(int[][] board, int val, int row, int col){
        return !inArray(getSquare(board, row, col), val);
    }

    private int[] getSquare(int[][] board, int row, int col){
        int[] square = new int[board[0].length];
        int square_pos_row = (int) row / 3;
        int square_pos_col = (int) col / 3;
        for (int i = 0; i < square.length; i++) {
            for (int j = square_pos_row * 3; j < (square_pos_row + 1) * 3; j++) {
                for (int k = square_pos_col * 3; k < (square_pos_col + 1) * 3; k++) {
                    square[i] = board[j][k];
                }
            }
        }
        return square;
    }

    private int[] getColumn(int[][] board, int index){
        int[] column = new int[board[0].length];
        for(int i=0; i<column.length; i++){
            column[i] = board[i][index];
        }
        return column;
    }

    private boolean inArray(int[] array, int val){
        for (int element : array){
            if (element == val) return true;
        }
        return false;
    }

    private boolean hasZeros(int[] array){
        for (int i = 0; i < array.length; i++){
            if (array[i] == 0) return true;
        }
        return false;
    }

}
