package hu.unideb.inf;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import static hu.unideb.inf.App.loadFXML;

public class GameController implements Initializable, Serializable {

    @FXML Button button_one;
    @FXML Button button_two;
    @FXML Button button_three;
    @FXML Button button_four;
    @FXML Button button_five;
    @FXML Button button_six;
    @FXML Button button_seven;
    @FXML Button button_eight;
    @FXML Button button_nine;
    @FXML Canvas canvas;

    SudokuBoard sudokuBoard;

    private int player_selected_row;
    private int player_selected_col;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sudokuBoard = App.getSudokuBoard();
        drawCurrentGameState();
        player_selected_row = 0;
        player_selected_col = 0;
    }

    public void drawCurrentGameState() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        initializeCanvas(context);
        highlightSelectedCell(context);
        drawInitialState(context);
        drawNumbersAddedByPlayer(context);
    }

    public void canvasMouseClicked() {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int mouse_x = (int) event.getX();
                int mouse_y = (int) event.getY();


                player_selected_row = (int) (mouse_y / 50);
                player_selected_col = (int) (mouse_x / 50);

                drawCurrentGameState();
            }
        });
    }
    public void buttonOnePressed() throws Exception {
        sudokuBoard.modifyCell(1, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonTwoPressed() throws Exception {
        sudokuBoard.modifyCell(2, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonThreePressed() throws Exception {
        sudokuBoard.modifyCell(3, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonFourPressed() throws Exception {
        sudokuBoard.modifyCell(4, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonFivePressed() throws Exception {
        sudokuBoard.modifyCell(5, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonSixPressed() throws Exception {
        sudokuBoard.modifyCell(6, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonSevenPressed() throws Exception {
        sudokuBoard.modifyCell(7, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonEightPressed() throws Exception {
        sudokuBoard.modifyCell(8, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    public void buttonNinePressed() throws Exception {
        sudokuBoard.modifyCell(9, player_selected_row, player_selected_col);
        drawCurrentGameState();
    }

    private void initializeCanvas(GraphicsContext context){
        context.clearRect(0, 0, 450, 450);
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {
                int position_y = row * 50 + 2;
                int position_x = col * 50 + 2;
                int width = 46;

                if (((row < 3 || 6 <= row) && (col < 3 || 6 <= col)) || ((3 <= row && row < 6) && (3 <= col && col < 6))){
                    context.setFill(Color.LIGHTGRAY);
                }
                else{
                    context.setFill(Color.WHITE);
                }

                context.fillRoundRect(position_x, position_y, width, width, 10, 10);
            }
        }
    }

    private void highlightSelectedCell(GraphicsContext context){
        context.setStroke(Color.DEEPSKYBLUE);
        context.setLineWidth(3);
        context.strokeRoundRect(player_selected_col * 50 + 2, player_selected_row * 50 + 2, 46, 46, 10, 10);
    }

    private void drawInitialState(GraphicsContext context){
        int[][] initial = sudokuBoard.getInitial();
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {
                int position_y = row * 50 + 30;
                int position_x = col * 50 + 20;
                context.setFill(Color.BLACK);
                context.setFont(new Font(20));
                if(initial[row][col]!=0) {
                    context.fillText(initial[row][col] + "", position_x, position_y);
                }
            }
        }
    }

    private void drawNumbersAddedByPlayer(GraphicsContext context){
        int[][] player = sudokuBoard.getPlayerBoard();
        for(int row = 0; row<9; row++) {
            for(int col = 0; col<9; col++) {
                int position_y = row * 50 + 30;
                int position_x = col * 50 + 20;
                context.setFill(Color.BLUEVIOLET);
                context.setFont(new Font(22));
                if(player[row][col]!=0) {
                    context.fillText(player[row][col] + "", position_x, position_y);
                }
            }
        }
    }

    public void keyboardInteraction(KeyEvent event) throws Exception {
        if (event.getCharacter().equals("w") && player_selected_row != 0) player_selected_row--;
        if (event.getCharacter().equals("s") && player_selected_row != 8) player_selected_row++;
        if (event.getCharacter().equals("a") && player_selected_col != 0) player_selected_col--;
        if (event.getCharacter().equals("d") && player_selected_col != 8) player_selected_col++;
        if (event.getCharacter().equals("1")) buttonOnePressed();
        if (event.getCharacter().equals("2")) buttonTwoPressed();
        if (event.getCharacter().equals("3")) buttonThreePressed();
        if (event.getCharacter().equals("4")) buttonFourPressed();
        if (event.getCharacter().equals("5")) buttonFivePressed();
        if (event.getCharacter().equals("6")) buttonSixPressed();
        if (event.getCharacter().equals("7")) buttonSevenPressed();
        if (event.getCharacter().equals("8")) buttonEightPressed();
        if (event.getCharacter().equals("9")) buttonNinePressed();
        drawCurrentGameState();
    }

    public void saveGame( ) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null){
            JSONObject boardJSON = sudokuBoard.getBoardJSON();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(boardJSON.toJSONString());
            fileWriter.flush();
        }
    }

    public void mainMenu( ) throws IOException {
        Parent fxml = loadFXML("main_menu");
        App.setRoot(fxml);
    }

    public void newGame() throws IOException {
        Parent fxml = loadFXML("new_game");
        App.setRoot(fxml);
    }
}
