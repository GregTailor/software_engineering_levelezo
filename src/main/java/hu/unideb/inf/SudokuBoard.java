package hu.unideb.inf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class SudokuBoard {

    private int[][] playerBoard;
    private int[][] initialBoard;

    public SudokuBoard(int difficulty){
        generateSudokuBoard(difficulty);
        this.playerBoard = new int[9][9];
    }

    public SudokuBoard(int[][] initialBoard){
        this.initialBoard = initialBoard;
        this.playerBoard = new int[9][9];
    }

    public SudokuBoard(File file) throws IOException {
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
        this.initialBoard = mapper.readValue(initialBoard, int[][].class);
        this.playerBoard = mapper.readValue(playerBoard, int[][].class);
    }

    public int[][] getInitial() {
        return initialBoard;
    }

    public int[][] getPlayerBoard() {
        return playerBoard;
    }

    public void modifyCell(int val, int row, int col) throws Exception {
        if (val < 1 || 9 < val) throw new Exception("Invalid value. Value can only be a number between 1 and 9!");
        if (playerBoard[row][col] == val) {
            playerBoard[row][col] = 0;
        }
        else {
            if (initialBoard[row][col] == 0)
                playerBoard[row][col] = val;
        }
    }

    private void generateSudokuBoard(int difficulty) {
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

    @SuppressWarnings("unchecked")
    public JSONObject getBoardJSON(){
        JSONArray playerBoard = new JSONArray();
        for (int i = 0; i < this.playerBoard.length; i++){
            JSONArray row = new JSONArray();
            for (int j = 0; j < this.playerBoard[i].length; j++){
                row.add(this.playerBoard[i][j]);
            }
            playerBoard.add(row);
        }
        JSONArray initialBoard = new JSONArray();
        for (int i = 0; i < this.initialBoard.length; i++){
            JSONArray row = new JSONArray();
            for (int j = 0; j < this.initialBoard[i].length; j++){
                row.add(this.initialBoard[i][j]);
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
