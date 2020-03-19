package hu.unideb.inf;


import javafx.scene.Parent;

import java.io.IOException;

import static hu.unideb.inf.App.loadFXML;

public class NewGameController {

    public void back() throws IOException {
        Parent fxml = loadFXML("main_menu");
        App.setRoot(fxml);
    }

    public void easyGame() throws IOException {
        App.setSudokuBoard(new SudokuBoard(1));
        Parent fxml = loadFXML("game");
        App.setRoot(fxml);
    }

    public void mediumGame() throws IOException {
        App.setSudokuBoard(new SudokuBoard(2));
        Parent fxml = loadFXML("game");
        App.setRoot(fxml);
    }

    public void hardGame() throws IOException {
        App.setSudokuBoard(new SudokuBoard(3));
        Parent fxml = loadFXML("game");
        App.setRoot(fxml);
    }

}
