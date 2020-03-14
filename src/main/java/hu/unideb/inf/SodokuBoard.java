package hu.unideb.inf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class SodokuBoard {

    private static int[][] playerBoard;
    private static int[][] initialBoard;
    private int[][] finalBoard;

    public SodokuBoard(int difficulty){
        generateSodokuBoard(difficulty);
        playerBoard = new int[9][9];
    }

    public SodokuBoard(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        String stringContent = content.toString();
        String initialBoard = stringContent.substring(0, ordinalIndexOf(stringContent, "\"", 3));
        String playerBoard = stringContent.substring(ordinalIndexOf(stringContent, "\"", 3));
        initialBoard = initialBoard.substring(initialBoard.indexOf("["), initialBoard.lastIndexOf("]") + 1);
        playerBoard = playerBoard.substring(playerBoard.indexOf("["), playerBoard.lastIndexOf("]") + 1);
        System.out.println(initialBoard);
        System.out.println(playerBoard);
        ObjectMapper mapper  = new ObjectMapper();
        SodokuBoard.initialBoard = mapper.readValue(initialBoard, int[][].class);
        SodokuBoard.playerBoard = mapper.readValue(playerBoard, int[][].class);
    }

    public int[][] getInitial() {
        return initialBoard;
    }

    public int[][] getPlayerBoard() {
        return playerBoard;
    }

    public static void modifyCell(int val, int row, int col) {
        if (playerBoard[row][col] == val) {
            playerBoard[row][col] = 0;
        }
        else {
            if (initialBoard[row][col] == 0)
                playerBoard[row][col] = val;
        }
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

    private void generateSodokuBoard(int difficulty) {
        initialBoard = new int[9][9];
        try{

            URL url;
            switch (difficulty){
                case 1:
                    url = new URL("https://sugoku.herokuapp.com/board?difficulty=easy");
                    break;
                case 2:
                    url = new URL("https://sugoku.herokuapp.com/board?difficulty=medium");
                    break;
                case 3:
                    url = new URL("https://sugoku.herokuapp.com/board?difficulty=hard");
                    break;
                default:
                    url = null;
            }
            if (url != null){
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                String board = content.toString().substring(content.indexOf("["), content.lastIndexOf("]") + 1);
                ObjectMapper mapper  = new ObjectMapper();
                initialBoard = mapper.readValue(board, int[][].class);
            }
            else{
                setDefaultBoard();
            }
        }catch (IOException e){
            setDefaultBoard();
        }
    }

    private void setDefaultBoard(){
        initialBoard = new int[][]
                {
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
    }

    private int countZeros(int[][] sodokuBoard){
        int count = 0;
        for (int i = 0; i < sodokuBoard.length; i++){
            for (int j = 0; j < sodokuBoard[i].length; j++){
                if (sodokuBoard[i][j] == 0) count++;
            }
        }
        return count;
    }

    public boolean solved(){
        generateFinalBoard();
        if (countZeros(finalBoard) != 0) return false;
        for (int i = 0; i < finalBoard.length; i++){
            for (int j = 0; j < finalBoard[i].length; j++){
                if (!valueFitsInRow(finalBoard, finalBoard[i][j], i)) return false;
                if (!valueFitsInColumn(finalBoard, finalBoard[i][j], j)) return false;
                if (!valueFitsInSquare(finalBoard, finalBoard[i][j], i, j)) return false;
            }
        }
        return true;
    }

    private void generateFinalBoard(){
        finalBoard = new int[9][9];
        for (int i = 0; i < playerBoard.length; i++){
            for (int j = 0; j < playerBoard[i].length; j++){
                if (playerBoard[i][j] != 0){
                    finalBoard[i][j] = playerBoard[i][j];
                }
                else {
                    finalBoard[i][j] = initialBoard[i][j];
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public JSONObject getBoardJSON(){
        JSONArray playerBoard = new JSONArray();
        for (int i = 0; i < SodokuBoard.playerBoard.length; i++){
            JSONArray row = new JSONArray();
            for (int j = 0; j < SodokuBoard.playerBoard[i].length; j++){
                row.add(SodokuBoard.playerBoard[i][j]);
            }
            playerBoard.add(row);
        }
        JSONArray initialBoard = new JSONArray();
        for (int i = 0; i < SodokuBoard.initialBoard.length; i++){
            JSONArray row = new JSONArray();
            for (int j = 0; j < SodokuBoard.initialBoard[i].length; j++){
                row.add(SodokuBoard.initialBoard[i][j]);
            }
            initialBoard.add(row);
        }
        JSONObject boardInformations = new JSONObject();
        boardInformations.put("playerBoard", playerBoard);
        boardInformations.put("initialBoard", initialBoard);
        return boardInformations;
    }

    private int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}
