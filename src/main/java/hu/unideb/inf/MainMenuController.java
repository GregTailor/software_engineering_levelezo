package hu.unideb.inf;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static hu.unideb.inf.App.loadFXML;

public class MainMenuController implements Initializable {

    @FXML Button button_resume_game;
    @FXML Button button_new_game;
    @FXML Button button_load_game;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void newGame() throws IOException {
        Parent fxml = loadFXML("new_game");
        App.setRoot(fxml);
    }

    public void resumeGame() throws IOException {
        Parent fxml = loadFXML("game");
        App.setRoot(fxml);
    }

    public void loadGame() {
    }

    public void exitGame() {
        Platform.exit();
    }
}
