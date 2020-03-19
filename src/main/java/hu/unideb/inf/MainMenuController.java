package hu.unideb.inf;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static hu.unideb.inf.App.loadFXML;

public class MainMenuController implements Initializable {

    @FXML Button button_resume_game;
    @FXML Button button_new_game;
    @FXML Button button_load_game;
    @FXML Button button_exit_game;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void newGame() throws IOException {
        Parent fxml = loadFXML("new_game");
        App.setRoot(fxml);
    }

    public void resumeGame() throws IOException {
        if (App.getSudokuBoard() != null) {
            Parent fxml = loadFXML("game");
            App.setRoot(fxml);
        }
    }

    public void loadGame() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showSaveDialog(button_load_game.getScene().getWindow());
        if (file != null) {
            SudokuBoard board = new SudokuBoard(file);
            App.setSudokuBoard(board);
            resumeGame();
        }
    }

    public void exitGame() throws IOException {
        System.out.println(App.getSudokuBoard().getBoardJSON());
        File latestFile = new File("latest.json");
        if (latestFile.exists() ){
            boolean deleted = latestFile.delete();
        }
        File file = new File("latest.json");
        JSONObject boardJSON = App.getSudokuBoard().getBoardJSON();
        System.out.println(boardJSON);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(boardJSON.toJSONString());
        fileWriter.close();
        Platform.exit();
    }
}
